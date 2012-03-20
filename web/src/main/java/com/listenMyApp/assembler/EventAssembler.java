package com.listenMyApp.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.listenMyApp.core.domain.Event;
import com.listenMyApp.core.domain.EventStatus;
import com.listenMyApp.dto.EventDTO;
import com.listenMyApp.dto.EventStatusDTO;

@Service
public class EventAssembler {
	
	public EventDTO toDTO (Event event){
		
		EventStatusDTO status = EventStatusDTO.CLOSED;
		
		if (event.getStatus() == EventStatus.OPEN){
			status = EventStatusDTO.OPEN;
		}
		
		final EventDTO eventDTO = new EventDTO(
				event.getId()
				, event.getEnvironment()
				, event.getCategory()
				, event.getMessage()
				, event.getEventDate()
				, event.getClassName()
				, event.getFileName()
				, event.getLineNumber()
				, event.getMethodName()
				, event.getTrace()
				, status
				);
		return eventDTO;
	}
	

	public List<EventDTO> toDTO(List<Event> events){
		final List<EventDTO> dtoList = new ArrayList<EventDTO>();
		for (Event event : events){
			dtoList.add(toDTO(event));
		}
		return dtoList;
	}
	
}
