package com.rest.facade;

import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;


public class DeployedEventFacade {
	
/*	public void jbossDeployedTest() throws Exception{
		final String event = new String("<teste/>");
		final HttpClient client = new DefaultHttpClient();

		HttpPost post = new HttpPost("http://localhost:8080/listenMyApp/services/events");
		StringEntity entity = new StringEntity(event);
		entity.setContentType("application/xml");
		post.setEntity(entity);
		
		HttpResponse response = client.execute(post);
		client.getConnectionManager().shutdown();
		
		
	}*/

}
