package com.listenMyApp.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.listenMyApp.dto.EventDTO;
import com.listenMyApp.dto.UserNavigationDTO;
import com.listenMyApp.service.EventService;
import com.listenMyApp.service.ServiceException;

@Component
@Scope("request")  
public class HomeBean extends BaseBean {
	private static final long serialVersionUID = 3918618880346342114L;
	@Autowired
	private EventService eventService;
	private List<EventDTO> events;
	private Boolean showSideTip = Boolean.FALSE;
	

    public void load()
    {
        if (loggedInUser.isLoggedIn())
        {
	    	try {
	    		events = eventService.getLastOpened(loggedInUser.getUser().getKey());
	    		loggedInUser.getUser().setNavigation(UserNavigationDTO.events.toString());
			} catch (Exception e) {
				facesUtils.setErrorMessage(e.getMessage());
			}
        }
    }
    
    
    public String getViewPath(){
    	try {
			if(loggedInUser.getProjects().size() == 0) {
				this.showSideTip = Boolean.FALSE;
				return "/pages/project_not_configured.jsf";
			}
		} catch (ServiceException e) {
			facesUtils.setErrorMessage(e.getMessage());
		}
		this.showSideTip = Boolean.TRUE;
		return "/pages/home.jsf";   	
    }
    
	public List<EventDTO> getEvents() {
		return events;
	}


	public void setEvents(List<EventDTO> events) {
		this.events = events;
	}


	public Boolean getShowSideTip() {
		return showSideTip;
	}

}
