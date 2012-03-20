package com.listenMyApp.service;

import com.listenMyApp.dto.UserDTO;

public interface UserService {
	
	UserDTO create(UserDTO userDTO) throws ServiceException;

	UserDTO login(UserDTO userDTO) throws ServiceException;

	void changePassword(String email, String oldPassword, String newPassword) throws ServiceException;

	void forgetPassword(String email) throws ServiceException;

	UserDTO activate(String userKey) throws ServiceException;

	void alter(String key, String name, String language,
			String idTimeZone, Boolean notify) throws ServiceException;
	
}
