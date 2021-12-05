package com.tre.demo.payment.paymentserv.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tre.demo.payment.paymentserv.api.model.PaymentRequest;
import com.tre.demo.payment.paymentserv.api.model.PaymentResponse;
import com.tre.demo.payment.paymentserv.api.model.TransactionResponse;
import com.tre.demo.payment.paymentserv.context.PaymentContext;
import com.tre.demo.payment.paymentserv.handler.CompletePaymentFlowHandler;
import com.tre.demo.payment.paymentserv.handler.GetTransactionStatusFlowHandler;
import com.tre.demo.payment.paymentserv.util.PaymentContextBuilder;

@RestController
@RequestMapping("/payment")
public class PaymentsController {
	
	@Autowired
	PaymentContextBuilder contextBuiler;
	
	@Autowired
	CompletePaymentFlowHandler completePaymentFlowHandler;
	
	@Autowired
	GetTransactionStatusFlowHandler getTransactionStatusFlowHandler;
	

	@PostMapping(path = "/pay", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public PaymentResponse completePayment(@Validated @RequestBody PaymentRequest request) {
		
		List<PaymentContext> paymentContextList = contextBuiler.build(request);
		
		
		completePaymentFlowHandler.setPaymentContextList(paymentContextList);
		completePaymentFlowHandler.setPartialPaymentAllowed(request.isPartialPaymentAllowed());
		
		completePaymentFlowHandler.process();
		
		return completePaymentFlowHandler.buildResponse();
	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public TransactionResponse getTransactionDetails(@PathVariable String id) {
		
		PaymentContext paymentContext = new PaymentContext();
		paymentContext.setPaymentId(id);
		
		List<PaymentContext> paymentContextList = Arrays.asList(paymentContext);
		
		getTransactionStatusFlowHandler.setPaymentContextList(paymentContextList);
		
		getTransactionStatusFlowHandler.process();
		
		return getTransactionStatusFlowHandler.buildResponse();
	}
}
