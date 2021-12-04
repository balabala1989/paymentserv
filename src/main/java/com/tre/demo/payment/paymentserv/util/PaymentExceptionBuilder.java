package com.tre.demo.payment.paymentserv.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tre.demo.payment.paymentserv.api.model.Error;
import com.tre.demo.payment.paymentserv.exceptions.PaymentFailureException;
import com.tre.demo.payment.paymentserv.exceptions.TransactionNotFoundException;

@ControllerAdvice
public class PaymentExceptionBuilder {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		if (ex instanceof PaymentFailureException) {
			Error error = new Error();
			error.setName("Payment Processing Failure");
			error.setMessage(ex.getMessage());
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else if (ex instanceof TransactionNotFoundException) {
			Error error = new Error();
			error.setName("Resource Not Found!!!");
			error.setMessage(ex.getMessage());
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
