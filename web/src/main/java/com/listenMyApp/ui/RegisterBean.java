package com.listenMyApp.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.listenMyApp.dto.LanguageDTO;
import com.listenMyApp.dto.TimezoneDTO;
import com.listenMyApp.dto.UserDTO;
import com.listenMyApp.service.ServiceException;
import com.listenMyApp.service.UserService;

@Component
@Scope("request")  
public class RegisterBean extends BaseBean {
	private static final long serialVersionUID = 6164188474957291710L;

	private String name = "";
	private String email = "";
	private String password = "";
	private String passwordConfirmation = "";
	private LanguageDTO language;
	private String timezone = "";
	private Boolean notify;
	private TimezoneDTO timezoneDTO = TimezoneDTO.instance();
	private Map<String, String> timezones = new LinkedHashMap<String, String>();
	private LanguageDTO[] languages = LanguageDTO.values();

	@Autowired
	private UserService userService;

	public String load(){
		timezones = timezoneDTO.getAvailableTimeZones();
		return null;
	}

	public String register() {
		UserDTO userDTO = new UserDTO();
		userDTO.setName(name);
		userDTO.setEmail(email);
		userDTO.setPassword(password);
		userDTO.setLanguage(language);
		userDTO.setIdTimeZone(timezone);
		userDTO.setAccountId(1L);
		userDTO.setNotify(notify);

		try {
			userService.create(userDTO);

		} catch (ServiceException ex) {
			facesUtils.setErrorMessage(ex.getMessage());
			return null;
		}
		return facesUtils.beautify("login");
	}

	public String processUserRegistration() {
		try {
			userService.activate(params.getUserKey());
		} catch (ServiceException e) {
			facesUtils.setErrorMessage(e.getMessage());
			return null;
		}
		return facesUtils.beautify("login");
	}

	public void validateConfirmPassword(final FacesContext context,
			final UIComponent comp, final Object value) {
		Object password = facesUtils.getRequestParameter("Password");

		if (!password.equals(value) && !"".equals(value)) {
			throw new ValidatorException(new FacesMessage(
					"Passwords must match"));
		}
		return;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public void setTimezones(Map<String, String> timezones) {
		this.timezones = timezones;
	}
	
	public Map<String, String> getTimezones() {
		return timezones;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public LanguageDTO getLanguage() {
		return language;
	}

	public void setLanguage(LanguageDTO language) {
		this.language = language;
	}

	public LanguageDTO[] getLanguages() {
		return languages;
	}

	public void setLanguages(LanguageDTO[] languages) {
		this.languages = languages;
	}

	public Boolean getNotify() {
		return notify;
	}

	public void setNotify(Boolean notify) {
		this.notify = notify;
	}
	
	
	
	

}
