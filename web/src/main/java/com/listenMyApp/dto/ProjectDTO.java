package com.listenMyApp.dto;

import java.util.List;

public class ProjectDTO {
	
	private Long id;
	private String key;
	private String name;
	private List<EventDTO> events;
	private Long cntOpenedEvents;
	private Long cntClosedEvents;
	private Long cntTotalEvents;
	
	public ProjectDTO(){}
	
	public ProjectDTO(Long id, String key, String name){
		this.id = id;
		this.key = key;
		this.name = name;
		
	}

	public ProjectDTO(String name) {
		this.name = name;
	}

	public ProjectDTO(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}
	
	public String getNameFirstCharacters() {
		if (name.length()>10){
			return name.substring(0,10).concat("...");
		}
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public List<EventDTO> getEvents() {
		return events;
	}

	public void setEvents(List<EventDTO> events) {
		this.events = events;
	}

	public Long getCntOpenedEvents() {
		return cntOpenedEvents;
	}

	public void setCntOpenedEvents(Long cntOpenedEvents) {
		this.cntOpenedEvents = cntOpenedEvents;
	}

	public Long getCntClosedEvents() {
		return cntClosedEvents;
	}

	public void setCntClosedEvents(Long cntClosedEvents) {
		this.cntClosedEvents = cntClosedEvents;
	}

	public Long getCntTotalEvents() {
		return cntTotalEvents;
	}

	public void setCntTotalEvents(Long cntTotalEvents) {
		this.cntTotalEvents = cntTotalEvents;
	}

	
	
}
