package com.rest.application.facade.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.rest.application.facade.RestFacade;


public class RestFacadeImpl implements RestFacade {

	public Response add(final String exception) {
		ResponseBuilder builder = Response.ok("okay");
		
	//UnauthorizedException - Response.status(401);
	//BadRequestException - Response.status(400);
	//NotFoundException - Response.status(404);
		return builder.build();
	}

}
