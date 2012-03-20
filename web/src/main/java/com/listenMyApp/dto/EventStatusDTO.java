package com.listenMyApp.dto;

/**
 * Responsavel por encapsular os status de um evento.<br>
 * 
 * @author Marco Rojo
 * Data: 19/02/2010
 */
public enum EventStatusDTO {
	UNKNOW(0)
	, OPEN(1)
	, CLOSED(2);
	
	private int value;
	
	private EventStatusDTO(int _value){
		value = _value;
	}
	
	public int getValue(){
		return value;
	}

}
