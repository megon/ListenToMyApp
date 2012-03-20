package com.listenMyApp.ui;

import java.util.List;

import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.listenMyApp.dto.ProjectDTO;
import com.listenMyApp.dto.UserNavigationDTO;
import com.listenMyApp.service.ProjectService;

@Component
@RequestScoped
public class MyProjectsBean  extends BaseBean {
	private static final long serialVersionUID = -512583139712227222L;
	@Autowired
	private ProjectService projectService;

	private List<ProjectDTO> myProjects;
	

    public void load(){	
    	
        if (loggedInUser.isLoggedIn())
        {
	    	try {
	    		myProjects = projectService.getByUserKey(loggedInUser.getUser().getKey());
	    		loggedInUser.getUser().setNavigation(UserNavigationDTO.projects.toString());
			} catch (Exception e) {
				facesUtils.setErrorMessage(e.getMessage());
			}
        }
    }


	public List<ProjectDTO> getMyProjects() {
		return myProjects;
	}


	public void setMyProjects(List<ProjectDTO> myProjects) {
		this.myProjects = myProjects;
	}
    
    
}
