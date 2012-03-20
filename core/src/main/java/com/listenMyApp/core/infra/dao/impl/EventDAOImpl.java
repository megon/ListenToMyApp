package com.listenMyApp.core.infra.dao.impl;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.listenMyApp.core.domain.Event;
import com.listenMyApp.core.domain.EventException;
import com.listenMyApp.core.domain.EventRepository;
import com.listenMyApp.core.domain.EventStatus;

@Repository
public class EventDAOImpl implements EventRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Event event) throws EventException{
		try{
			entityManager.persist(event);
		}
		catch (EntityExistsException entityExistsEx){
			throw new EventException("error.event.alreadyExists");
		}
		catch (Exception ex){
			throw new EventException("error.event.persistError");
		}
		
	}

	@Override
	public Event getById(Long id) throws EventException {
		final Event event = entityManager.find(Event.class, id);
		if (event == null){
			throw new EventException("error.event.notFound");
		}
		return event;	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Event> getByLastOpened(Long id) throws EventException {
		final Query query = entityManager.createNamedQuery("Event.lastOpened");
		query.setMaxResults(15);
		query.setParameter("idStatus", EventStatus.OPEN);
		query.setParameter("idUser", id);
		final List<Event> events = query.getResultList(); 
		return events;
	}

	@Override
	public Long getCntOpenedEvents(Long projectId) throws EventException {
		try{
			final Query query = entityManager.createNamedQuery("Event.cntOpenedEvents");
			query.setParameter("projectId", projectId);
			query.setParameter("idStatus", EventStatus.OPEN);
			return (Long)query.getSingleResult();
		}
		catch (Exception ex){
			throw new EventException("error.event.persistError");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Event> filterByStatus(Long projectId,
			EventStatus eventStatus) throws EventException{
		try{
			final Query query = entityManager.createNamedQuery("Event.filterByStatus");
			query.setParameter("projectId", projectId);
			query.setParameter("idStatus", eventStatus);
			return query.getResultList();
		}
		catch (Exception ex){
			throw new EventException("error.event.persistError");
		}
	}
	
}
