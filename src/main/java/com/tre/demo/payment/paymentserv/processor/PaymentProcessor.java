package com.tre.demo.payment.paymentserv.processor;

import java.util.concurrent.Callable;

import com.tre.demo.payment.paymentserv.context.PaymentContext;
import com.tre.demo.payment.paymentserv.processor.clients.Client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class PaymentProcessor implements Callable<PaymentContext> {
	
	private PaymentContext paymentContext;
	private ProcessorRequest request;
	private ProceessorResponse response;
	
	@Override
	public PaymentContext call() throws Exception {
		return execute();
	}
	
	
	
	/*
	 * Step 1 - Convert to format expected by the Payment processor
	 * Step 2 - Create client for any kind of integration like REST or SOAP
	 * Step 3 - Call the endpoint and get the response
	 * Step 4 - Convert Response to PaymentContext 
	 */
	public PaymentContext execute() {
		request = convertToProcessorRequest(paymentContext);
		response = getClient().makeRequest(request);
		paymentContext = convertProcessorResponseToContext(paymentContext, response);
		return paymentContext;
	}
	
	
	public abstract ProcessorRequest convertToProcessorRequest(PaymentContext paymentContext);
	
	public abstract Client getClient();
	
	public abstract PaymentContext convertProcessorResponseToContext(PaymentContext paymentContext, ProceessorResponse response);
	
}
