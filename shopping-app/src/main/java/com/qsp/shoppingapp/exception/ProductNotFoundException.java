package com.qsp.shoppingapp.exception;

public class ProductNotFoundException extends RuntimeException {

	private String message = "No products to show";
	
	public ProductNotFoundException() {
	}

	public ProductNotFoundException(String message) {
		super();
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
