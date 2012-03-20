package com.listenMyApp.core.infra.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.listenMyApp.core.domain.Project;
import com.listenMyApp.core.domain.ProjectException;
import com.listenMyApp.core.domain.ProjectRepository;


@Repository
public class ProjectDAOImpl implements ProjectRepository {

	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Project project) {
		entityManager.persist(project);
	}

	
	@Override
	public Project getByKey(String projectKey) throws ProjectException {
		final Query query = entityManager.createNamedQuery("Project.key");
		query.setParameter("key", projectKey);
		try{
			final Project project = (Project)query.getSingleResult();
			return project;
		}
		catch (NoResultException ex){
			throw new ProjectException("error.project.notFound");
		}
	}


	@Override
	public Project getById(Long id) throws ProjectException {
		final Query query = entityManager.createNamedQuery("Project.id");
		query.setParameter("id", id);
		try{
			final Project project = (Project)query.getSingleResult();
			return project;
		}
		catch (NoResultException ex){
			throw new ProjectException("error.project.notFound");
		}
	}


	@Override
	public void remove(Project project) throws ProjectException {
		try{
			entityManager.remove(project);
		}
		catch (Exception ex){
			throw new ProjectException(ex.getMessage());
		}
		
	}


	@Override
	public Project getByUserKeyProjectId(String userKey, Long id)
			throws ProjectException {
		final Query query = entityManager.createNamedQuery("Project.userKeyProjectId");
		query.setParameter("userKey", userKey);
		query.setParameter("id", id);
		try{
			final Project project = (Project)query.getSingleResult();
			return project;
		}
		catch (NoResultException ex){
			throw new ProjectException("error.project.notFound");
		}
	}
	
}
