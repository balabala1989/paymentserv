package com.tre.demo.payment.paymentserv.handler;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tre.demo.payment.paymentserv.api.model.PaymentResponse;
import com.tre.demo.payment.paymentserv.api.model.TransactionResponse;
import com.tre.demo.payment.paymentserv.context.PaymentContext;
import com.tre.demo.payment.paymentserv.context.PaymentInstructionContext;
import com.tre.demo.payment.paymentserv.entity.Transaction;
import com.tre.demo.payment.paymentserv.entity.TransactionDetails;
import com.tre.demo.payment.paymentserv.exceptions.TransactionNotFoundException;
import com.tre.demo.payment.paymentserv.mapper.PaymentContextToPaymentResponseMapper;
import com.tre.demo.payment.paymentserv.mapper.TransactionToPaymentContextMapper;
import com.tre.demo.payment.paymentserv.repository.TransactionDetailRepository;
import com.tre.demo.payment.paymentserv.repository.TransactionRepository;

@Service
@Scope("prototype")
@Qualifier("getTransactionFlowHandler")
public class GetTransactionStatusFlowHandler extends RequestFlowHandler {

	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	TransactionDetailRepository transactionDetailRepository;
	
	@Autowired
	TransactionToPaymentContextMapper transactionToPaymentContextMapper;
	
	@Autowired
	PaymentContextToPaymentResponseMapper paymentContextToPaymentResponseMapper;
	
	private PaymentContext responsePaymentContext;
	private List<TransactionDetails> transactionDetails;
	private List<PaymentInstructionContext> responsePaymentInstructionContext;
	private Transaction transactionDao;
	
	@Override
	public void process() {
		
		PaymentContext requestPaymentContext = getPaymentContextList().get(0);
		
		Optional<Transaction> transaction = transactionRepository.findById(requestPaymentContext.getPaymentId());
		
		if (transaction.isPresent()) {
			transactionDao = transaction.get();
			transactionDetails = transactionDetailRepository.findTransactionDetailsByPaymentId(requestPaymentContext.getPaymentId());
			responsePaymentContext = transactionToPaymentContextMapper.mapTransactionToPaymentContext(transactionDao);
			
			if (transactionDetails != null && !transactionDetails.isEmpty()) {
				responsePaymentInstructionContext = transactionToPaymentContextMapper.mapTransactionDetailsToPaymentContextList(transactionDetails);
			}
			
		}
		else {
			throw new TransactionNotFoundException("Transaction with paymentId - " + requestPaymentContext.getPaymentId() + " not found!!!");
		}

	}

	@Override
	public <T> T buildResponse() {
		
		PaymentResponse paymentResponse = paymentContextToPaymentResponseMapper.mapPaymentContextToPaymentResponse(responsePaymentContext);
		TransactionResponse transactionResponse = new TransactionResponse();
		transactionResponse.setPaymentResponse(paymentResponse);
		transactionResponse.setInstructionStatus(paymentContextToPaymentResponseMapper.mapInstructionContextToStatusList(responsePaymentInstructionContext));
		return (T) transactionResponse;
		
	}


}
