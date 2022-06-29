package com.adp.payment.exception;

public class InvalidBillAmountException  extends RuntimeException{
	String message;
	
	public InvalidBillAmountException(String message) {
		super(message);
		this.message = message;
	}

	public InvalidBillAmountException() {
		
	}
}
