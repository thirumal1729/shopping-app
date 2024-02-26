package com.qsp.shoppingapp.exception;

public class PlaceOrderToReviewException extends RuntimeException {

	private String message = "Place the order first";
	
	public PlaceOrderToReviewException() {
	}

	public PlaceOrderToReviewException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
