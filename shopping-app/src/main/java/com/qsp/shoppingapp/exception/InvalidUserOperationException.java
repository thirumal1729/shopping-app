package com.qsp.shoppingapp.exception;

public class InvalidUserOperationException extends RuntimeException {

	private String message = "User not allowed to do this operation";
	
	public InvalidUserOperationException() {
	}

	public InvalidUserOperationException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
