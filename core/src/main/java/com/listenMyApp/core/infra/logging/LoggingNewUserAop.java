package com.listenMyApp.core.infra.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingNewUserAop {
	
	Logger logger = Logger.getLogger(LoggingNewUserAop.class);
	
	@Before("execution(* com.listenMyApp.core.application.UserFacade.create(..)) && args(name, email, ..)")
	public void logNewUser(String name, String email){
		final StringBuffer message = new StringBuffer();
		message.append("novo usuario: ".concat(name));
		message.append(System.getProperty("line.separator"));
		message.append("email: ".concat(email));
		logger.info(message);
	}
}
