package com.listenMyApp.service;

import java.util.List;

import com.listenMyApp.dto.ProjectDTO;

public interface ProjectService {
	
	ProjectDTO create(String userKey, ProjectDTO projectDTO) throws ServiceException;

	void remove(String userKey, Long projectId) throws ServiceException;

	ProjectDTO get(String userKey, Long projectId) throws ServiceException;

	List<ProjectDTO> getByUserKey(String userKey) throws ServiceException;

	void addUserToProject(String userKey, Long idProject, String email) throws ServiceException;

	String generateKey(String userKey, Long ProjectId) throws ServiceException;
	
	void alter(String userKey, Long projectId, String projectName, String projectKey) throws ServiceException;


}
