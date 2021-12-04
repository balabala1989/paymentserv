package com.tre.demo.payment.paymentserv.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tre.demo.payment.paymentserv.context.PaymentContext;
import com.tre.demo.payment.paymentserv.context.PaymentStatus;
import com.tre.demo.payment.paymentserv.context.SwishPaymentContext;
import com.tre.demo.payment.paymentserv.processor.clients.Client;
import com.tre.demo.payment.paymentserv.processor.clients.SoapClient;
import com.tre.demo.payment.paymentserv.util.IdGeneration;

@Service
@Scope("prototype")
public class SwishPaymentProcessor extends PaymentProcessor {

	@Autowired
	SoapClient soapClient;
	
	
	@Override
	public ProcessorRequest convertToProcessorRequest(PaymentContext paymentContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client getClient() {
		return soapClient;
	}

	@Override
	public PaymentContext convertProcessorResponseToContext(PaymentContext paymentContext,
			ProceessorResponse response) {
		SwishPaymentContext context = (SwishPaymentContext) paymentContext.getPaymentInstructionContext().getBackingPaymentMethodContext();
		if (context.getUserId().equals("testException@swish.com"))
			paymentContext.getPaymentInstructionContext().setPaymentStatus(PaymentStatus.FAILED);
		else
			paymentContext.getPaymentInstructionContext().setPaymentStatus(PaymentStatus.SUCCESS);
		paymentContext.getPaymentInstructionContext().getBackingPaymentMethodContext().setVendorTransactionId(IdGeneration.generateId(20));
		return paymentContext;
	}

	

}
