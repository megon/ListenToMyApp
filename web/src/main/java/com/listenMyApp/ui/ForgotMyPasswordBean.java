package com.listenMyApp.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.listenMyApp.service.ServiceException;
import com.listenMyApp.service.UserService;

@Component
@Scope("request")
public class ForgotMyPasswordBean extends BaseBean {
	private static final long serialVersionUID = 2386445009863732022L;

	private String email;
	
	@Autowired
	private UserService userService; 
	
	
	public String mailMeMyPassword(){
		try {
			userService.forgetPassword(email);
		} catch (ServiceException e) {
			facesUtils.setErrorMessage(e.getMessage());
			return null;
		}
		return facesUtils.beautify("forgotMyPasswordSent");
	}
	

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
