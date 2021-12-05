package com.tre.demo.payment.paymentserv.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tre.demo.payment.paymentserv.api.model.PaymentResponse;
import com.tre.demo.payment.paymentserv.context.PaymentContext;
import com.tre.demo.payment.paymentserv.context.PaymentMethodEnum;
import com.tre.demo.payment.paymentserv.context.PaymentStatus;
import com.tre.demo.payment.paymentserv.entity.Transaction;
import com.tre.demo.payment.paymentserv.entity.TransactionDetails;
import com.tre.demo.payment.paymentserv.exceptions.PaymentFailureException;
import com.tre.demo.payment.paymentserv.mapper.PaymentContextToPaymentResponseMapper;
import com.tre.demo.payment.paymentserv.mapper.PaymentContextToTransactionMapper;
import com.tre.demo.payment.paymentserv.mapper.PaymentInstructionToTransactionDetailsMapper;
import com.tre.demo.payment.paymentserv.mapper.TransactionToPaymentContextMapper;
import com.tre.demo.payment.paymentserv.processor.BankPaymentProcessor;
import com.tre.demo.payment.paymentserv.processor.CardPaymentProcessor;
import com.tre.demo.payment.paymentserv.processor.PayPalPaymentProcessor;
import com.tre.demo.payment.paymentserv.processor.PaymentProcessor;
import com.tre.demo.payment.paymentserv.processor.StripePaymentProcessor;
import com.tre.demo.payment.paymentserv.processor.SwishPaymentProcessor;
import com.tre.demo.payment.paymentserv.repository.TransactionDetailRepository;
import com.tre.demo.payment.paymentserv.repository.TransactionRepository;
import com.tre.demo.payment.paymentserv.util.IdGeneration;
import com.tre.demo.payment.paymentserv.util.PaymentConstants;

@Service
@Scope("prototype")
@Qualifier("completePaymentFlowHandler")
public class CompletePaymentFlowHandler extends RequestFlowHandler {
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	TransactionDetailRepository transactionDetailRepository;
	
	@Autowired
	CardPaymentProcessor cardPaymentProcessor;
	
	@Autowired
	BankPaymentProcessor bankPaymentProcessor;
	
	@Autowired
	PayPalPaymentProcessor paypalPaymentProcessor;
	
	@Autowired
	StripePaymentProcessor stripePaymentProcessor;
	
	@Autowired
	SwishPaymentProcessor swishPaymentProcessor;
	
	@Autowired
	PaymentContextToTransactionMapper paymenContextToTransactionMapper;
	
	@Autowired
	PaymentInstructionToTransactionDetailsMapper payemntInstructionToTransactionDetailsMapper;
	
	@Autowired
	TransactionToPaymentContextMapper transactionToPaymentContextMapper;
	
	@Autowired
	PaymentContextToPaymentResponseMapper paymentContextToPaymentResponseMapper;
	
	
	private Transaction transactionRecord;
	
	private boolean isPartialPaymentAllowed;
	
	private PaymentContext responsePaymentContext;


	@Override
	public void process(){
		
		//Check for idempotency
		List<PaymentContext> paymentContextList = getPaymentContextList();
		
		Optional<Transaction> transaction = transactionRepository.findById(paymentContextList.get(0).getPaymentId());
		if (transaction.isPresent()) {
			transactionRecord = transaction.get();
			responsePaymentContext = transactionToPaymentContextMapper.mapTransactionToPaymentContext(transactionRecord);
			return;
		}
		else {
			transactionRecord = paymenContextToTransactionMapper.mapPaymentContextToTransaction(paymentContextList.get(0));
			transactionRecord.setStatus(PaymentStatus.PENDING.name());
			transactionRepository.save(transactionRecord);
			transactionRecord = transactionRepository.findById(paymentContextList.get(0).getPaymentId()).get();
		}
		

		PaymentProcessor paymentProcessor;
		
		for (int index = 0; index < paymentContextList.size(); index++) {
			PaymentContext paymentContext = paymentContextList.get(index);
			generateInternalTransactionId(paymentContext);
			paymentProcessor = getPaymentProcessor(paymentContext);
			paymentProcessor.setPaymentContext(paymentContext);
			paymentContext = paymentProcessor.execute();
			
			if (!isPartialPaymentAllowed && paymentContext.getPaymentInstructionContext().getPaymentStatus() != PaymentStatus.SUCCESS) {
				StringBuilder builder = new StringBuilder();
				builder.append(paymentContext.getPaymentInstructionContext().getPaymentMethod())
					   .append(" payment is declined!!!!");
				transactionRecord.setStatus(PaymentStatus.FAILED.name());
				
				transactionRepository.save(transactionRecord);
				throw new PaymentFailureException(builder.toString());
			}
				
			
			paymentContextList.set(index, paymentContext);
		}
		

		for (PaymentContext paymentContext : paymentContextList) {
			TransactionDetails details = payemntInstructionToTransactionDetailsMapper.mapPaymentContextToTransaction(paymentContext.getPaymentInstructionContext());
			details.setTimeCreated(LocalDateTime.now());
			details.setPaymentId(paymentContext.getPaymentId());
			transactionDetailRepository.save(details);
		}
		
		
		long successCount = paymentContextList.parallelStream().filter(context -> context.getPaymentInstructionContext().getPaymentStatus() == PaymentStatus.SUCCESS).count();
			
		if (successCount != paymentContextList.size())
			transactionRecord.setStatus(successCount == 0 ? PaymentStatus.FAILED.name() : PaymentStatus.PARTIAL_SUCCESS.name());
		else
			transactionRecord.setStatus(PaymentStatus.SUCCESS.name());
		
		transactionRepository.save(transactionRecord);
		
		responsePaymentContext = transactionToPaymentContextMapper.mapTransactionToPaymentContext(transactionRecord);
	}

	@Override
	public <T> T buildResponse() {
		PaymentResponse response = paymentContextToPaymentResponseMapper.mapPaymentContextToPaymentResponse(responsePaymentContext);
		response.setGetStatusUrl(PaymentConstants.SERVER_HOST_ADDRESS + responsePaymentContext.getPaymentId());
		return (T) response;
	}

	private PaymentProcessor getPaymentProcessor(PaymentContext paymentContext) {
		PaymentProcessor paymentProcessor = null;

		if (paymentContext.getPaymentInstructionContext().getPaymentMethod() == PaymentMethodEnum.CARD)
			paymentProcessor = cardPaymentProcessor;
		else if (paymentContext.getPaymentInstructionContext().getPaymentMethod() == PaymentMethodEnum.BANK)
			paymentProcessor = bankPaymentProcessor;
		else if (paymentContext.getPaymentInstructionContext().getPaymentMethod() == PaymentMethodEnum.PAYPAL)
			paymentProcessor = paypalPaymentProcessor;
		else if (paymentContext.getPaymentInstructionContext().getPaymentMethod() == PaymentMethodEnum.SWISH)
			paymentProcessor = swishPaymentProcessor;
		else if (paymentContext.getPaymentInstructionContext().getPaymentMethod() == PaymentMethodEnum.STRIPE)
			paymentProcessor = stripePaymentProcessor;

		return paymentProcessor;
	}
	
	private void generateInternalTransactionId(PaymentContext context) {
		context.getPaymentInstructionContext().getBackingPaymentMethodContext().setInternalTransactionId(IdGeneration.generateId(16));
	}


	public void setPartialPaymentAllowed(boolean isPartialPaymentAllowed) {
		this.isPartialPaymentAllowed = isPartialPaymentAllowed;
	}

}
