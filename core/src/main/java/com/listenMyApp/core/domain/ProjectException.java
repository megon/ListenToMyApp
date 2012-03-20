package com.listenMyApp.core.domain;

/**
 * Exceptions relacionadas a Projeto.
 * 
 * @author Marcelo Menezes
 * Data: 15/02/2010
 */
public class ProjectException extends Exception {

	private static final long serialVersionUID = -3206657996219913191L;

	public ProjectException() {
		super();
	}

	public ProjectException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ProjectException(String key) {
		super(key);
	}

	public ProjectException(Throwable arg0) {
		super(arg0);
	}

}