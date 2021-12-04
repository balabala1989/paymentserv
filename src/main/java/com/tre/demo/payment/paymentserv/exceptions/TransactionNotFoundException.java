package com.tre.demo.payment.paymentserv.exceptions;

public class TransactionNotFoundException extends RuntimeException{

	
	private static final long serialVersionUID = 6605314666547738212L;
	
	public TransactionNotFoundException(String message) {
		super(message);
	}

}
