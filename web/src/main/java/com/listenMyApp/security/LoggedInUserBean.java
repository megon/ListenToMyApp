package com.listenMyApp.security;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.listenMyApp.constants.Constants;
import com.listenMyApp.dto.LanguageDTO;
import com.listenMyApp.dto.ProjectDTO;
import com.listenMyApp.dto.UserDTO;
import com.listenMyApp.jsf.http.HttpResources;
import com.listenMyApp.service.ProjectService;
import com.listenMyApp.service.ServiceException;

@Component
@RequestScoped
public class LoggedInUserBean {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private HttpResources httpResources;
	private List<ProjectDTO> projects;

	public UserDTO getUser() {
		if (isLoggedIn()) {
			return (UserDTO) httpResources.getSession(true).getAttribute(Constants.USER_SESSION_KEY);
		}
		return new UserDTO();
	}

	public List<ProjectDTO> getProjects() throws ServiceException {
		projects = new ArrayList<ProjectDTO>();
		if (isLoggedIn()) {
			projects = projectService.getByUserKey(getUser().getKey()); 
		}
		return projects;
	}

	public boolean isLoggedIn() {
		HttpSession session = httpResources.getSession(true);
		return session.getAttribute(Constants.USER_SESSION_KEY) != null;
	}
	
	public String getLocale(){
		if (isLoggedIn()) {
			return getUser().getLanguage().toString();
		}
		return LanguageDTO.en.toString();
	}

}
