package com.listenMyApp.core.application.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.listenMyApp.core.application.ProjectFacade;
import com.listenMyApp.core.domain.EventRepository;
import com.listenMyApp.core.domain.EventStatus;
import com.listenMyApp.core.domain.Project;
import com.listenMyApp.core.domain.ProjectException;
import com.listenMyApp.core.domain.ProjectRepository;
import com.listenMyApp.core.domain.User;
import com.listenMyApp.core.domain.UserException;
import com.listenMyApp.core.domain.UserRepository;

@Service
public class ProjectFacadeImpl implements ProjectFacade {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;

	@Override
	@Transactional
	public Project create(final String userKey, final String projectName) throws ProjectException, UserException {

		Project project = new Project(projectName);
		project.createKey();
		
		User user = userRepository.getByKey(userKey);
		
		Integer numberProjectsAlowed = user.getAccount().getNumberOfProjectsAlowed();
		if (user.getProjects().size() >= numberProjectsAlowed)
			throw new ProjectException("error.project.hit.limit");

		user.getProjects().add(project); 
		
		projectRepository.save(project);
		return project;
	}

	@Override
	@Transactional
	public void delete(final String userKey, final Long projectId)
			throws ProjectException, UserException {
		
		final User user = userRepository.getByKey(userKey);
		final Project project = projectRepository.getById(projectId);
		
		user.getProjects().remove(project);
		projectRepository.remove(project);
		
	}

	@Override
	@Transactional
	public Project get(final String userKey, final Long projectId)
			throws ProjectException, UserException {
		try{
			final Project project = projectRepository.getByUserKeyProjectId(userKey, projectId);
			project.setCntOpenedEvents((long)eventRepository.filterByStatus(project.getId(), EventStatus.OPEN).size());
			project.setCntClosedEvents((long)eventRepository.filterByStatus(project.getId(), EventStatus.CLOSED).size());
			project.setCntTotalEvents((long)projectRepository.getById(project.getId()).getEvents().size());
			return project;
		} catch (Exception ex){
			throw new ProjectException(ex.getMessage());
		}
	}

	@Override
	@Transactional
	@SuppressWarnings("unused")
	public void addUserToProject(final String userKey, final Long projectId, final String emailUserToAdd) throws ProjectException, UserException {
		
		final User userOwner = userRepository.getByKey(userKey);
		final User userAgregated = userRepository.getByEmail(emailUserToAdd);
		
		final Project project = projectRepository.getById(projectId);
		
		Integer numberProjectsAlowed = userAgregated.getAccount().getNumberOfProjectsAlowed();
		if (userAgregated.getProjects().size() >= numberProjectsAlowed)
			throw new ProjectException("error.project.hit.limit");

		userAgregated.getProjects().add(project); 		
	}

	@Override
	public List<Project> getByUserKey(String userKey) throws UserException {
		try{
			final User user = userRepository.getByKey(userKey);
			final List<Project> projects = new ArrayList<Project>();
			for (Project project : user.getProjects()){
				project.setCntOpenedEvents((long)eventRepository.filterByStatus(project.getId(), EventStatus.OPEN).size());
				project.setCntClosedEvents((long)eventRepository.filterByStatus(project.getId(), EventStatus.CLOSED).size());
				project.setCntTotalEvents((long)projectRepository.getById(project.getId()).getEvents().size());
				projects.add(project);
			}
			return projects;
		}
		catch (Exception ex){
			throw new UserException(ex.getMessage());
		}
	}

	@Override
	@SuppressWarnings("unused")
	public String generateKey(String userKey, Long projectId)
			throws UserException, ProjectException {

		final User user = userRepository.getByKey(userKey);
		final Project project = projectRepository.getById(projectId);
		return project.createKey();
	}	
	
	@Override
	@SuppressWarnings("unused")
	@Transactional
	public void alter(String userKey, Long projectId, String projectName,
			String projectKey) throws UserException, ProjectException {

		final User user = userRepository.getByKey(userKey);
		final Project project = projectRepository.getById(projectId);
		project.setName(projectName);
		project.setKey(projectKey);
	}

}
