package com.listenMyApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.listenMyApp.assembler.UserAssembler;
import com.listenMyApp.core.application.UserFacade;
import com.listenMyApp.core.domain.User;
import com.listenMyApp.dto.UserDTO;
import com.listenMyApp.service.ServiceException;
import com.listenMyApp.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	

	@Autowired
	private UserFacade userFacade;
	
	@Autowired
	private UserAssembler userAssembler;
	
	@Override
	public UserDTO create(UserDTO userDTO) throws ServiceException{
		try{
			final User user = userFacade.create(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getAccountId(), userDTO.getLanguage().name(), userDTO.getIdTimeZone(), userDTO.getNotify());
			return userAssembler.toDTO(user);
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	@Override
	public UserDTO login(UserDTO userDTO) throws ServiceException {
		try{
			
			final User user = userFacade.login(userDTO.getEmail(), userDTO.getPassword());
			
			final UserDTO loggedUserDTO = userAssembler.toDTO(user);
			
			return loggedUserDTO;
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	@Override
	public void changePassword(String email, String oldPassword,
			String newPassword) throws ServiceException {
		try{
			userFacade.changePassword(email, oldPassword, newPassword);
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	@Override
	public void forgetPassword(String email) throws ServiceException {
		try{
			userFacade.forgetPassword(email);
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	@Override
	public UserDTO activate(String userKey) throws ServiceException {
		try{
			final User user = userFacade.activate(userKey);
			return userAssembler.toDTO(user);
		}
		catch (Exception ex){
			throw new ServiceException(ex.getMessage());
		}
	}

	@Override
	public void alter(String key, String name, 
			String language, String idTimeZone, Boolean notify) throws ServiceException {
		try{
			userFacade.alter(key, name, language, idTimeZone, notify);
		}
		catch (Exception ex){
			throw new ServiceException(ex);
		}
		
		
	}

}
