package com.listenMyApp.ui;

import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.listenMyApp.dto.ProjectDTO;
import com.listenMyApp.service.ProjectService;

@Component
@RequestScoped
public class CreateProjectBean extends BaseBean {

	private static final long serialVersionUID = 5491276557531363552L;

	
	@Autowired
	private ProjectService projectService;
	private ProjectDTO project;
	
	public void load(){
		project = new ProjectDTO();
	}

	public String create() {
		try {
			project = projectService.create(loggedInUser.getUser().getKey(), project);
			facesUtils.setInfoMessage("projectCreated");

		} catch (Exception e) {
			facesUtils.setErrorMessage(e.getMessage());
		}
		return null;
	}
	
	public ProjectDTO getProject() {
		return project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}
}
