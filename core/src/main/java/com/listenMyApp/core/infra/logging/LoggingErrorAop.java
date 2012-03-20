package com.listenMyApp.core.infra.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingErrorAop {
	
	Logger logger = Logger.getLogger(LoggingErrorAop.class);
	
	@AfterThrowing(
			pointcut="execution(* com.listenMyApp.core.application.*.*(..))"
			, throwing="ex"
			)
	public void logError(Exception ex){
		logger.error(ex);
	}
}
