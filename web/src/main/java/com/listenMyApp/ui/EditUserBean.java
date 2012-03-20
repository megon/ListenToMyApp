package com.listenMyApp.ui;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.bean.RequestScoped;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.listenMyApp.dto.LanguageDTO;
import com.listenMyApp.dto.TimezoneDTO;
import com.listenMyApp.dto.UserDTO;
import com.listenMyApp.dto.UserNavigationDTO;
import com.listenMyApp.service.ServiceException;
import com.listenMyApp.service.UserService;

@Component
@RequestScoped
public class EditUserBean extends BaseBean {

	private static final long serialVersionUID = 5491276557531363552L;

	
	@Autowired
	private UserService userService;
	private UserDTO user;
	private Map<String, String> timezones = new LinkedHashMap<String, String>();
	private LanguageDTO[] languages = LanguageDTO.values();
	private boolean updated;
	private TimezoneDTO timezoneDTO = TimezoneDTO.instance();
	
	
	public void load() throws IOException, ServletException, ServiceException {
		user = loggedInUser.getUser();
		setUpdated(false);
		loggedInUser.getUser().setNavigation(UserNavigationDTO.myInformation.toString());
		timezones = timezoneDTO.getAvailableTimeZones();
	}

	
	public String alter() {
		try {
			userService.alter(loggedInUser.getUser().getKey(), user.getName(), user.getLanguage().toString(), user.getIdTimeZone(), user.getNotify());
			facesUtils.setInfoMessage("userUpdated");
			setUpdated(true);
		} catch (Exception e) {
			facesUtils.setErrorMessage(e.getMessage());
		}
		return null;
	}


	public UserDTO getUser() {
		return user;
	}


	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	public void setTimezones(Map<String, String> timezones) {
		this.timezones = timezones;
	}
	
	public Map<String, String> getTimezones() {
		return timezones;
	}


	public LanguageDTO[] getLanguages() {
		return languages;
	}


	public void setLanguages(LanguageDTO[] languages) {
		this.languages = languages;
	}
	
	
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	
	public boolean isUpdated() {
		return updated;
	}
	
}
