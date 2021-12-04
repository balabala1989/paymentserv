package com.tre.demo.payment.paymentserv.handler;

import java.util.List;

import com.tre.demo.payment.paymentserv.context.PaymentContext;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class RequestFlowHandler {
	
	private List<PaymentContext> paymentContextList;
	
	
	public abstract void process();
	public abstract <T> T buildResponse();
	
	
}
