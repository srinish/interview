package com.adp.payment.exception;

public class NotEnoughCoinsException extends RuntimeException{

	private String message;
	
	public NotEnoughCoinsException(String message) {
		super(message);
		this.message = message;
	}
	public NotEnoughCoinsException() {
		
	}
}
