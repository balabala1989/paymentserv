package com.tre.demo.payment.paymentserv.exceptions;

public class PaymentFailureException extends RuntimeException{

	private static final long serialVersionUID = 4895718961948666702L;
	
	public PaymentFailureException(String message) {
		super(message);
	}

}
