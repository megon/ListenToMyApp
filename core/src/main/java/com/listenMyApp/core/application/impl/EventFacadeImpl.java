package com.listenMyApp.core.application.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.listenMyApp.core.application.EventFacade;
import com.listenMyApp.core.domain.Event;
import com.listenMyApp.core.domain.EventException;
import com.listenMyApp.core.domain.EventRepository;
import com.listenMyApp.core.domain.EventStatus;
import com.listenMyApp.core.domain.Language;
import com.listenMyApp.core.domain.Project;
import com.listenMyApp.core.domain.ProjectException;
import com.listenMyApp.core.domain.ProjectRepository;
import com.listenMyApp.core.domain.User;
import com.listenMyApp.core.domain.UserException;
import com.listenMyApp.core.domain.UserRepository;
import com.listenMyApp.core.infra.mail.MailService;


@Service
public class EventFacadeImpl implements EventFacade {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private MailService mailService;
	
	private static final String EVENT_TEMPLATE_FILE_PT = "eventMailTemplate_pt.html"; 
	private static final String EVENT_TEMPLATE_FILE_EN = "eventMailTemplate_en.html";
	private static final String EVENT_SUBJECT_EN = "listenToMyApp - New event notification";
	private static final String EVENT_SUBJECT_PT = "listenToMyApp - Notificacao de evento";
	private static final String EVENT_FROM = "event-noreply@listentomyapp.com";
	
	private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	private final Validator validator = validatorFactory.getValidator();

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Event create(String projectKey, String environment,
			String apiVersion, String clientName, String clientVersion,
			String category, String message, String className,
			String fileName, String lineNumber, String methodName, String trace)
			throws EventException, ProjectException {

		final Project project = projectRepository.getByKey(projectKey);
		final Date now = new Date();
		
		final Event event = new Event(apiVersion, clientName, clientVersion, environment.toUpperCase(),
						category.toUpperCase(), message, now, className, fileName
						, lineNumber, methodName, trace, EventStatus.OPEN);
		event.setProjectKey(projectKey);
		
		validateEvent(event);
		event.setProject(project);
		eventRepository.save(event);
		
		try{
			
			//FIXME colocar um tratamento de erro descente.
			for (User user : project.getUsers()){
				if (user.isNotify()){
					try{
						sendEventNotification(user, event);
					}
					catch (Exception mse){
						mse.printStackTrace();
					}
				}
			}
			return event;
		}
		catch (Exception ex){
			throw new EventException(ex.getMessage());
		}
	}

	
	@Override
	public List<Event> getLastOpened(String userKey) throws EventException, UserException {
		final User user = userRepository.getByKey(userKey);
		final List<Event> events = eventRepository.getByLastOpened(user.getId());
		return events;
	}	

	@Override
	@Transactional
	public Event close(String userKey, Long id) throws EventException, UserException {
		@SuppressWarnings("unused")
		final User user = userRepository.getByKey(userKey);
		final Event event = eventRepository.getById(id);
		event.setStatus(EventStatus.CLOSED);
		return event;
	}
	

	@Override
	public List<Event> getByProjectKey(String projectKey)
			throws ProjectException {
		final Project project = projectRepository.getByKey(projectKey);
		return project.getEvents();
	}
	
	@Override
	public Long getCntOpenedEvents(Long projectId) throws EventException {
		return eventRepository.getCntOpenedEvents(projectId); 
	}
	
	
	
	@Override
	public List<Event> filterByStatus(Long projectId, EventStatus ... eventStatus)
			throws EventException {
		if (eventStatus.length != 0){
			return eventRepository.filterByStatus (projectId, eventStatus[0]);
		}
		else{
			try{
				return projectRepository.getById(projectId).getEvents();
			}
			catch (Exception ex){
				throw new EventException(ex.getMessage());
			}
			
		}
		
	}
	
	@Override
	public List<Event> getByProjectId(Long projectId) throws ProjectException {
		final Project project = projectRepository.getById(projectId);
		return project.getEvents();
	}
	
	@Override
	public Event get(Long eventId) throws EventException {
		return eventRepository.getById(eventId);
	}
	
	
	
	
	private void sendEventNotification(final User user, final Event event) throws EventException{
		try{
			if (user.getLanguage().equals(Language.en)){
				final String body = mailService.createBody(EVENT_TEMPLATE_FILE_EN, getEventModel(event));
				mailService.send(user.getEmail(), EVENT_FROM, EVENT_SUBJECT_EN, body);
			}
			else {
				final String body = mailService.createBody(EVENT_TEMPLATE_FILE_PT, getEventModel(event));
				mailService.send(user.getEmail(), EVENT_FROM, EVENT_SUBJECT_PT, body);
			}
			
		}
		catch (Exception ex){
			throw new EventException(ex.getMessage());
		}
	}

	
	private Map<String, String> getEventModel(Event event){
		final Map<String, String> eventModel = new HashMap<String, String>();
		eventModel.put("environment", event.getEnvironment());
		eventModel.put("message", event.getMessage());
		eventModel.put("eventDate", event.getEventDate().toString());
		eventModel.put("className", event.getClassName());
		eventModel.put("fileName", event.getFileName());
		eventModel.put("methodName", event.getMethodName());
		eventModel.put("trace", event.getTrace());
		eventModel.put("project", event.getProject().getName());
		eventModel.put("status", event.getStatus().name());
		return eventModel;
	}
	
	private void validateEvent(final Event event) throws EventException{
		final Set<ConstraintViolation<Event>> constraintViolations = validator.validate(event);
		final StringBuffer messages = new StringBuffer();

		if (constraintViolations.size() > 0){
			for (Iterator<ConstraintViolation<Event>> iterator = constraintViolations.iterator(); iterator.hasNext();){
				messages.append(iterator.next().getMessage());
				messages.append(System.getProperty("line.separator"));
			}
			throw new EventException(messages.toString());
		}
	}
}
