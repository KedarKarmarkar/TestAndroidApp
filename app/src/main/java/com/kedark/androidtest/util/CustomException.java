package com.kedark.androidtest.util;

/**
 * Class to represent the custom Exception.
 * @author Pleximus
 *
 */

public class CustomException extends Exception{

	private static final long serialVersionUID = 1L;

	public CustomException(String message){
		super(message);
	}
	
	public CustomException(String message, Throwable throwable){
		super(message, throwable);
	}
	
}
