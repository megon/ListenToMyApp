package com.listenMyApp.ui;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.listenMyApp.jsf.FacesUtils;
import com.listenMyApp.security.LoggedInUserBean;

@SuppressWarnings("serial")
public abstract class BaseBean implements Serializable {

    @Autowired
    protected ParamsBean params;
	
	@Autowired
	protected FacesUtils facesUtils;

	@Autowired
	protected LoggedInUserBean loggedInUser;
	
}
