package com.listenMyApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.listenMyApp.assembler.ProjectAssembler;
import com.listenMyApp.core.application.ProjectFacade;
import com.listenMyApp.core.domain.Project;
import com.listenMyApp.dto.ProjectDTO;
import com.listenMyApp.service.ProjectService;
import com.listenMyApp.service.ServiceException;

@Service
public class ProjectServiceImpl implements ProjectService {
	

	@Autowired
	private ProjectFacade projectFacade;
	
	@Autowired
	private ProjectAssembler assembler;
	
	@Override
	public ProjectDTO create(String userKey, ProjectDTO projectDTO) throws ServiceException{
		try{
			final Project project = projectFacade.create(userKey, projectDTO.getName());
			return assembler.toDTO(project);
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}
	
	
	@Override
	public void remove(String userKey, Long projectId) throws ServiceException{
		try{
			projectFacade.delete(userKey, projectId);
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}
	
	
	@Override
	public ProjectDTO get(String userKey, Long projectId) throws ServiceException{
		try{
			final Project project = projectFacade.get(userKey, projectId);
			return assembler.toDTO(project);
			
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}


	@Override
	public List<ProjectDTO> getByUserKey(String userKey)
			throws ServiceException {
		try {
			final List<Project> projects = projectFacade.getByUserKey(userKey);
			return assembler.toDTO(projects);
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}


	@Override
	public void addUserToProject(String userKey, Long projectId, String email)
			throws ServiceException {
		try{
			projectFacade.addUserToProject(userKey, projectId, email);
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
		
	}


	@Override
	public String generateKey(String userKey, Long projectId)
			throws ServiceException {
		try{
			final Project project = projectFacade.get(userKey, projectId);
			return project.createKey();
		}
		catch (Exception ex){
			throw new ServiceException(ex);
		}
	}


	@Override
	public void alter(String userKey, Long projectId,
			String projectName, String projectKey) throws ServiceException {

		try {
			projectFacade.alter(userKey, projectId, projectName, projectKey);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
	}
	
	
}
