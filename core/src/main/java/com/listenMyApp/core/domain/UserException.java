package com.listenMyApp.core.domain;


/**
 * Exceptions relacionadas a usuario.
 * 
 * @author Marco Rojo
 * Data: 14/02/2010
 */
public class UserException extends Exception {
	
	private static final long serialVersionUID = -2394797522655249597L;

	public UserException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public UserException(String key) {
		super(key);
	}

	public UserException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
