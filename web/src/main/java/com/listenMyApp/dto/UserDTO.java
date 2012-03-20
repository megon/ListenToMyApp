package com.listenMyApp.dto;

import java.util.List;

public class UserDTO {
	
	private String name;
	private String email;
	private String password;
	private String key;
	private Long accountId;
	private List<ProjectDTO> projects;
	private LanguageDTO language;
	private List<EventDTO> lastOpenedEvents;
	private Boolean active; 
	private String idTimeZone;
	private Boolean notify;
	private String navigation;
	
	public UserDTO(){}
	
	public UserDTO(String name, String key, String email, String password, Long accountId, String language, Boolean active, String idTimeZone, boolean notify){
		this.name = name;
		this.key = key;
		this.email = email;
		this.password = password;
		this.accountId = accountId;
		this.language = LanguageDTO.valueOf(language);
		this.active = active;
		this.idTimeZone = idTimeZone;
		this.notify = notify;
	}
	
	public UserDTO(String name, String email, String password, Long accountId, String language, String idTimeZone, Boolean notify){
		this.name = name;
		this.email = email;
		this.password = password;
		this.accountId = accountId;
		this.language = LanguageDTO.valueOf(language);
		this.idTimeZone = idTimeZone;
		this.notify = notify;
	}
	
	
	public UserDTO(String email, String password){
		this.email = email;
		this.password = password;
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
	
	public Long getAccountId() {
		return accountId;
	}
	
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}

	public void setProjects(List<ProjectDTO> projects) {
		this.projects = projects;
	}

	public List<ProjectDTO> getProjects() {
		return projects;
	}

	public LanguageDTO getLanguage() {
		return language;
	}

	public void setLanguage(LanguageDTO language) {
		this.language = language;
	}

	public List<EventDTO> getLastOpenedEvents() {
		return lastOpenedEvents;
	}

	public void setLastOpenedEvents(List<EventDTO> lastOpenedEvents) {
		this.lastOpenedEvents = lastOpenedEvents;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}


	public String getIdTimeZone() {
		return idTimeZone;
	}

	public void setIdTimeZone(String idTimeZone) {
		this.idTimeZone = idTimeZone;
	}

	public Boolean getNotify() {
		return notify;
	}

	public void setNotify(Boolean notify) {
		this.notify = notify;
	}
	
	public void setNavigation(String navigation) {
		this.navigation = navigation;
	}
	
	public String getNavigation() {
		return navigation;
	}
}
