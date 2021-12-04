package com.tre.demo.payment.paymentserv.processor.clients;

import com.tre.demo.payment.paymentserv.processor.ProceessorResponse;
import com.tre.demo.payment.paymentserv.processor.ProcessorRequest;

public interface Client {

	public ProceessorResponse makeRequest(ProcessorRequest request);
}
