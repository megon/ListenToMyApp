package com.listenMyApp.service;

import java.util.List;

import com.listenMyApp.dto.EventDTO;
import com.listenMyApp.dto.EventStatusDTO;

public interface EventService {

	List<EventDTO> getByProjectKey(String projectKey) throws ServiceException;

	List<EventDTO> getLastOpened(String userKey) throws ServiceException;

	EventDTO close(String userKey, Long eventId) throws ServiceException;

	Long getCntOpenedEvents(Long projectId) throws ServiceException;

	List<EventDTO> filterByStatus(Long projectId, EventStatusDTO ... eventStatus) throws ServiceException;

	List<EventDTO> getByProjectId(Long projectId) throws ServiceException;
	
	EventDTO get(Long eventId) throws ServiceException;

}
