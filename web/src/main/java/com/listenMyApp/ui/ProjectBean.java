package com.listenMyApp.ui;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.listenMyApp.dto.EventDTO;
import com.listenMyApp.dto.EventStatusDTO;
import com.listenMyApp.dto.ProjectDTO;
import com.listenMyApp.service.EventService;
import com.listenMyApp.service.ProjectService;

@Component
@RequestScoped
public class ProjectBean  extends BaseBean {
	private static final long serialVersionUID = -512583139712227222L;
	@Autowired
	private EventService eventService;	
	@Autowired
	private ProjectService projectService;
	private List<EventDTO> events = new ArrayList<EventDTO>();
	private ProjectDTO currentProject = new ProjectDTO();

	

    public void load(){	
        if (loggedInUser.isLoggedIn())
        {
	    	try {
	    		currentProject = projectService.get(loggedInUser.getUser().getKey(), params.getCurrentProjectID());
	    		if (params.getEventStatus() != null){
					if (params.getEventStatus().equalsIgnoreCase(EventStatusDTO.OPEN.toString())){
						events = eventService.filterByStatus(params.getCurrentProjectID(), EventStatusDTO.OPEN);
					} 
					else if (params.getEventStatus().equalsIgnoreCase(EventStatusDTO.CLOSED.toString())){
						events = eventService.filterByStatus(params.getCurrentProjectID(), EventStatusDTO.CLOSED);
					}
					else{
						events = eventService.filterByStatus(params.getCurrentProjectID());
					}
	    		}
	    		else{
	    			events = eventService.filterByStatus(params.getCurrentProjectID(), EventStatusDTO.OPEN);
	    		}
			} catch (Exception e) {
				currentProject.setId(0L);
				facesUtils.setErrorMessage(e.getMessage());
			}
        }
    }


	public List<EventDTO> getEvents() {
		return events;
	}


	public void setEvents(List<EventDTO> events) {
		this.events = events;
	}




	public ProjectDTO getCurrentProject() {
		return currentProject;
	}


	public void setCurrentProject(ProjectDTO currentProject) {
		this.currentProject = currentProject;
	}
	
}
