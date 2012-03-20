package com.listenMyApp.ui;

import java.io.IOException;

import javax.faces.bean.RequestScoped;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.listenMyApp.dto.ProjectDTO;
import com.listenMyApp.service.ProjectService;
import com.listenMyApp.service.ServiceException;

@Component
@RequestScoped
public class EditProjectBean extends BaseBean {

	private static final long serialVersionUID = 5491276557531363552L;

	
	@Autowired
	private ProjectService projectService;
	private ProjectDTO currentProject;
	
	
	public String load() throws IOException, ServletException, ServiceException {

		try{
			currentProject = projectService.get(loggedInUser.getUser().getKey(), params.getCurrentProjectID());
		}
		catch (Exception ex){
			facesUtils.setErrorMessage(ex.getMessage());
		}
		return null;
	}

	
	public void regenerateKey(){
		try {
			currentProject.setKey(projectService.generateKey(loggedInUser.getUser().getKey(), currentProject.getId()));
			
		} catch (ServiceException e) {
			facesUtils.setErrorMessage(e.getMessage());
		}
	}
	
	
	public String updateProject() {
		try {
			projectService.alter(loggedInUser.getUser().getKey(), currentProject.getId(), currentProject.getName(), currentProject.getKey());
			facesUtils.setInfoMessage("projectUpdated");
		} catch (Exception e) {
			facesUtils.setErrorMessage(e.getMessage());
		}
		return null;
	}

	public ProjectDTO getCurrentProject() {
		return currentProject;
	}


	public void setCurrentProject(ProjectDTO currentProject) {
		this.currentProject = currentProject;
	}
	
	
	

}
