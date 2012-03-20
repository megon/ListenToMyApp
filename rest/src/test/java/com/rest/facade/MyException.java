package com.rest.facade;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("MyException")
public class MyException {
	
	@XStreamAlias("key")
	private String key;
	
	@XStreamAlias("env")
	private String env;
	
	@XStreamAlias("message")
	private String message;
	
	@XStreamAlias("trace")
	private String trace;
	
	@XStreamAlias("className")
	private String className;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	
	
}
