package com.listenMyApp.rest.service.impl;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class EventServiceException extends WebApplicationException {

	private static final long serialVersionUID = -6840584032615918746L;

	public EventServiceException(Status status, String message) {
		super(Response.status(status).entity(message).type("text/plain").build());
	}
	
	public EventServiceException(Status status) {
		super(status);
	}
	
}
