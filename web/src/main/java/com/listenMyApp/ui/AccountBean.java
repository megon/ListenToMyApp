package com.listenMyApp.ui;

import java.io.IOException;

import javax.faces.bean.RequestScoped;
import javax.servlet.ServletException;

import org.springframework.stereotype.Component;

import com.listenMyApp.dto.UserDTO;
import com.listenMyApp.dto.UserNavigationDTO;
import com.listenMyApp.service.ServiceException;

@Component
@RequestScoped
public class AccountBean extends BaseBean {
	private static final long serialVersionUID = -8132662388482932927L;

	private UserDTO user;
	
	public void load() throws IOException, ServletException, ServiceException {
		user = loggedInUser.getUser();
		loggedInUser.getUser().setNavigation(UserNavigationDTO.myAccount.toString());
	}



}
