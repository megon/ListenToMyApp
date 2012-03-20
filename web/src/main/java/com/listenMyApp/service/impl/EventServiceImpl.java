package com.listenMyApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.listenMyApp.assembler.EventAssembler;
import com.listenMyApp.core.application.EventFacade;
import com.listenMyApp.core.domain.Event;
import com.listenMyApp.core.domain.EventStatus;
import com.listenMyApp.dto.EventDTO;
import com.listenMyApp.dto.EventStatusDTO;
import com.listenMyApp.service.EventService;
import com.listenMyApp.service.ServiceException;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventFacade eventFacade;
	
	@Autowired
	private EventAssembler eventAssembler;

	@Override
	public List<EventDTO> getByProjectKey(String projectKey)
			throws ServiceException {
		
		try{
			final List<Event> events = eventFacade.getByProjectKey(projectKey);
			return eventAssembler.toDTO(events);
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	@Override
	public List<EventDTO> getLastOpened(String userKey) throws ServiceException {
		try{
			final List<Event> events = eventFacade.getLastOpened(userKey);
			return eventAssembler.toDTO(events);
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	@Override
	public EventDTO close(String userKey, Long eventId) throws ServiceException {
		try{
			final Event event = eventFacade.close(userKey, eventId);
			return eventAssembler.toDTO(event);
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
		
	}

	@Override
	public Long getCntOpenedEvents(Long projectId) throws ServiceException {
		try{
			return eventFacade.getCntOpenedEvents(projectId);
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	@Override
	public List<EventDTO> filterByStatus(Long projectId, EventStatusDTO ... eventStatusDTO)
			throws ServiceException {
		try{
			if (eventStatusDTO.length != 0){
				if (eventStatusDTO[0] == EventStatusDTO.CLOSED){
					return eventAssembler.toDTO(eventFacade.filterByStatus(projectId, EventStatus.CLOSED)); 
				}
				else{
					return eventAssembler.toDTO(eventFacade.filterByStatus(projectId, EventStatus.OPEN));
				}
			}
			else{
				return eventAssembler.toDTO(eventFacade.filterByStatus(projectId));
			}
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	@Override
	public List<EventDTO> getByProjectId(Long projectId)
			throws ServiceException {
		try{
			return eventAssembler.toDTO(eventFacade.getByProjectId(projectId));
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	@Override
	public EventDTO get(Long eventId) throws ServiceException {
		try{
			return eventAssembler.toDTO(eventFacade.get(eventId));			
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
		
	}
}
