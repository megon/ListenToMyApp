package com.listenMyApp.dto;

import java.util.Date;


public class EventDTO {
	
	public EventDTO(Long id, String environment, String category, String message, Date eventDate, String className
			, String fileName, String lineNumber, String methodName, String trace
			, EventStatusDTO status){
		setId(id);
		setEnvironment(environment);
		setCategory(category);
		setMessage(message);
		setEventDate(eventDate);
		setClassName(className);
		setFileName(fileName);
		setLineNumber(lineNumber);
		setMethodName(methodName);
		setTrace(trace);
		setStatus(status);
	}	
	
	private Long id;
	private String environment;
	private String category;
	private String message;
	private Date eventDate;
	private String className;
	private String fileName;
	private String lineNumber;
	private String methodName;
	private String trace;
	private EventStatusDTO status;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEnvironment() {
		return environment;
	}
	
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Date getEventDate() {
		return eventDate;
	}
	
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getLineNumber() {
		return lineNumber;
	}
	
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public String getTrace() {
		return trace;
	}
	
	public void setTrace(String trace) {
		this.trace = trace;
	}

	public EventStatusDTO getStatus() {
		return status;
	}

	public void setStatus(EventStatusDTO status) {
		this.status = status;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return category;
	}

}
