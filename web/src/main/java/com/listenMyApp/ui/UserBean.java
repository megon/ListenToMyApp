package com.listenMyApp.ui;

import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.listenMyApp.service.ServiceException;
import com.listenMyApp.service.UserService;

@Component
@RequestScoped
public class UserBean extends BaseBean {
	
	private static final long serialVersionUID = -7155584672718262003L;
	@Autowired
	private UserService userService; 
	
	private String login;
	private String oldPassword;
	private String newPassword;
	private String repeatNewPassword;
	
	public String changeMyPassword(){
		try {
			//TODO: implementar validato pra ver se senha nova confere com a repetida
			userService.changePassword(login, oldPassword, newPassword);
		} catch (ServiceException e) {
			facesUtils.setErrorMessage(e.getMessage());
			return null;
		}
		return facesUtils.beautify("login");
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatNewPassword() {
		return repeatNewPassword;
	}

	public void setRepeatNewPassword(String repeatNewPassword) {
		this.repeatNewPassword = repeatNewPassword;
	}


}
