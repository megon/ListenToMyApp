package com.listenMyApp.ui;

import javax.faces.bean.RequestScoped;

import org.hibernate.cfg.JoinedSubclassFkSecondPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.listenMyApp.jsf.FacesUtils;
import com.listenMyApp.service.ProjectService;

@Component
@RequestScoped
public class DeleteProjectBean extends BaseBean {

	private static final long serialVersionUID = 5491276557531363552L;
	
	@Autowired
	private ProjectService projectService;
	
	public String delete() {
        if (loggedInUser.isLoggedIn())
        {
        	final FacesUtils facesUtils = FacesUtils.getInstance();
	    	try {
	    		Long projectId = Long.valueOf(facesUtils.getRequestParameter("id"));
	    		projectService.remove(loggedInUser.getUser().getKey(), projectId);
			} catch (Exception e) {
				facesUtils.setErrorMessage(e.getMessage());
			}
        }
        return "pretty:";
	}
	
}
