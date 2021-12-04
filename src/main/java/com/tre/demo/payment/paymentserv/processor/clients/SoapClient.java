package com.tre.demo.payment.paymentserv.processor.clients;

import org.springframework.stereotype.Service;

import com.tre.demo.payment.paymentserv.processor.ProceessorResponse;
import com.tre.demo.payment.paymentserv.processor.ProcessorRequest;

@Service
public class SoapClient implements Client {

	@Override
	public ProceessorResponse makeRequest(ProcessorRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
