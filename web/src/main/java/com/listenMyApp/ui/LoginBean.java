package com.listenMyApp.ui;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.listenMyApp.constants.Constants;
import com.listenMyApp.dto.UserDTO;
import com.listenMyApp.dto.UserNavigationDTO;
import com.listenMyApp.jsf.http.HttpResources;
import com.listenMyApp.service.ServiceException;
import com.listenMyApp.service.UserService;

@Component
@Scope("request")  
public class LoginBean extends BaseBean {
	private static final long serialVersionUID = 2046634519798089474L;
	private String userName = "";
	private String password = "";
	
	@Autowired
	private HttpResources httpResources;
	
	@Autowired
	private UserService userService;
	
	public String doLogin() throws IOException, ServletException, ServiceException {
		final UserDTO user = new UserDTO(userName, password);
		
		try{
			final UserDTO loggedUser = userService.login(user);
			loggedUser.setNavigation(UserNavigationDTO.events.toString());
			setUserInSession(loggedUser);
			return facesUtils.beautify("home");
		}
		catch (Exception ex){
			facesUtils.setErrorMessage(ex.getMessage());
			return null;
		}
	}
	
    public String doLogout() throws IOException {
    	invalidateUserSession();
    	return facesUtils.beautify("login");
    }
 
	
	private void setUserInSession(UserDTO user) {
		httpResources.getSession(true).setAttribute(Constants.USER_SESSION_KEY, user);
	}

	private void invalidateUserSession() {
 		httpResources.getSession(false).invalidate();
	}
	

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
