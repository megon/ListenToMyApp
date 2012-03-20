package com.listenMyApp.ui;

import javax.faces.bean.RequestScoped;

import org.springframework.stereotype.Component;

@Component
@RequestScoped
public class ParamsBean {
	
	private String eventStatus;
	private String projectName;
	private String userKey;
	private Long currentProjectID;
	private Long currentEventId;
	

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	
	public Long getCurrentProjectID() {
		return currentProjectID;
	}


	public void setCurrentProjectID(Long currentProjectID) {
		this.currentProjectID = currentProjectID;
	}

	public Long getCurrentEventId() {
		return currentEventId;
	}

	public void setCurrentEventId(Long currentEventId) {
		this.currentEventId = currentEventId;
	}
	
	
	

}