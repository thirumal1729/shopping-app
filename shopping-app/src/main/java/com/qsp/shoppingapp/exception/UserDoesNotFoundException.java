package com.qsp.shoppingapp.exception;

public class UserDoesNotFoundException extends RuntimeException{

	private String message = "user does not exist";
	
	public UserDoesNotFoundException() {
	}

	public UserDoesNotFoundException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
