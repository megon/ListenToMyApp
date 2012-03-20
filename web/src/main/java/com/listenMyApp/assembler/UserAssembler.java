package com.listenMyApp.assembler;

import org.springframework.stereotype.Service;

import com.listenMyApp.core.domain.User;
import com.listenMyApp.dto.UserDTO;

@Service
public class UserAssembler {

	public UserDTO toDTO(User user){
		final UserDTO userDTO = new UserDTO(user.getName(), user.getKey(), user.getEmail(), user.getPassword(), user.getAccount().getId(), user.getLanguage().toString(), user.isActive(), user.getIdTimeZone(), user.isNotify());
		
		return userDTO;
	}
	
	
	public User toDomain(UserDTO userDTO){
		return null;
	}
	
}
