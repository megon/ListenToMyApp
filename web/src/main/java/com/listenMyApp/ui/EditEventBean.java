package com.listenMyApp.ui;

import java.io.IOException;

import javax.faces.bean.RequestScoped;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.listenMyApp.dto.EventDTO;
import com.listenMyApp.service.EventService;
import com.listenMyApp.service.ServiceException;

@Component
@RequestScoped
public class EditEventBean extends BaseBean {

	private static final long serialVersionUID = 5491276557531363552L;

	
	@Autowired
	private EventService eventService;
	private EventDTO event;
	
	
	public String load() throws IOException, ServletException, ServiceException {

		try{
			event = eventService.get(params.getCurrentEventId());
		}
		catch (Exception ex){
			facesUtils.setErrorMessage(ex.getMessage());
		}
		return null;
	}
	
	public String close() {
		try{
			eventService.close(loggedInUser.getUser().getKey(), event.getId());
			facesUtils.setInfoMessage("eventClosed");
			event = eventService.get(event.getId());
		}
		catch (Exception e) {
		facesUtils.setErrorMessage(e.getMessage());
		}
		return null;
	}

	
	public void setEvent(EventDTO event) {
		this.event= event;
	}
	
	public EventDTO getEvent() {
		return event;
	}
}
