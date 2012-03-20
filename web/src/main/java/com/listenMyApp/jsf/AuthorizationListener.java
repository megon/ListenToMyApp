package com.listenMyApp.jsf;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContextImpl;

import com.listenMyApp.constants.Constants;
import com.ocpsoft.pretty.PrettyContext;

public class AuthorizationListener implements PhaseListener {

	private static final long serialVersionUID = -7375451372647212704L;
	
	public void afterPhase(PhaseEvent event) {
		//http://code.google.com/p/prettytest/source/browse/trunk/prettyTest/src/main/java/it/jflower/prettytest/web/UrlParsingBean.java
		FacesContext facesContext = event.getFacesContext();
		//String currentPage = facesContext.getViewRoot().getViewId();
		//String currentPage = PrettyContext.getCurrentInstance().getRequestURL().getURL();	 //TODO: prettyfaces 3.0.1
		String currentPage = PrettyContext.getCurrentInstance().getOriginalUri();

		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		Object currentUser = session.getAttribute(Constants.USER_SESSION_KEY);

		if (!isFreePassPage(currentPage) && currentUser == null) {
			NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
			nh.handleNavigation(facesContext, null, "pretty:login");
		}
	}

	//FIXME: Code smells here...fix as soon as possible.
	private boolean isFreePassPage(String currentPage) {
		if (currentPage.contains("/login")
				|| currentPage.contains("/register/free")
				|| currentPage.contains("/confirmRegistration/")
				|| currentPage.contains("/forgotMyPassword")
				|| currentPage.contains("/forgotMyPasswordSent")
				|| currentPage.contains("/confirMyNewPassword/")
				
			) {
			return true;
		}
		return false;
	}

	public void beforePhase(PhaseEvent event) {
		//The code bellow is justa workaround for PrimeFaces 2.0.2
		//http://primefaces.prime.com.tr/forum/viewtopic.php?f=3&t=2355
		//http://primefaces.prime.com.tr/forum/viewtopic.php?f=3&t=2335
		FacesContext fc = event.getFacesContext();
		ExternalContext ec = fc.getExternalContext();
		new RequestContextImpl(ec);
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}




