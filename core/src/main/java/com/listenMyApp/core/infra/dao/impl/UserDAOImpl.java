package com.listenMyApp.core.infra.dao.impl;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import com.listenMyApp.core.domain.User;
import com.listenMyApp.core.domain.UserException;
import com.listenMyApp.core.domain.UserRepository;

@Repository
public class UserDAOImpl implements UserRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	public User login(final String email, final String password) throws UserException {
		final Query query = entityManager.createNamedQuery("User.email");
		query.setParameter("email", email);
		
		try{
			final User user = (User)query.getSingleResult();
			
			if ( ! user.isActive()){
				throw new UserException("error.user.notActive");
			}
			
			if ( ! user.getPassword().equals(password)){
				throw new UserException("error.user.invalidPassword");
			}
			return user;
		}
		catch (NoResultException ex){
			throw new UserException("error.user.notFound");
		}
	}

	@Override
	public void save(User user) throws UserException{
		try{
			entityManager.persist(user);
		}
		catch (EntityExistsException entityExistsEx){
			throw new UserException("error.user.alreadyExists");
		}
		catch (PersistenceException pEx){
			if (pEx.getCause().getClass() == ConstraintViolationException.class){
				throw new UserException("error.user.alreadyExists");
			}
			else{
				throw new UserException("error.user.persistError");
			}
		}
		catch (Exception ex){
			throw new UserException("error.user.persistError");
		}
		
	}
	
	@Override
	public User getByKey(String userKey) throws UserException {
		final Query query = entityManager.createNamedQuery("User.key");
		query.setParameter("key", userKey);
		try{
			final User user = (User)query.getSingleResult();
			return user;
		}
		catch (NoResultException ex){
			throw new UserException("error.user.notFound");
		}
	}
	
	
	@Override
	public User getByEmail(String email) throws UserException {
		final Query query = entityManager.createNamedQuery("User.email");
		query.setParameter("email", email);
		try{
			final User user = (User)query.getSingleResult();
			return user;
		}
		catch (NoResultException ex){
			throw new UserException("error.user.email.notFound");
		}
	}	
}
