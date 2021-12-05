package com.tre.demo.payment.paymentserv.handler;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.tre.demo.payment.paymentserv.context.CardPaymentContext;
import com.tre.demo.payment.paymentserv.context.PaymentContext;
import com.tre.demo.payment.paymentserv.context.PaymentInstructionContext;
import com.tre.demo.payment.paymentserv.context.PaymentMethodEnum;
import com.tre.demo.payment.paymentserv.entity.Transaction;
import com.tre.demo.payment.paymentserv.entity.TransactionDetails;
import com.tre.demo.payment.paymentserv.exceptions.TransactionNotFoundException;
import com.tre.demo.payment.paymentserv.mapper.PaymentContextToPaymentResponseMapper;
import com.tre.demo.payment.paymentserv.mapper.TransactionToPaymentContextMapper;
import com.tre.demo.payment.paymentserv.repository.TransactionDetailRepository;
import com.tre.demo.payment.paymentserv.repository.TransactionRepository;

@RunWith(MockitoJUnitRunner.class)
class GetTransactionStatusFlowHandlerTest {

	
	@Mock
	TransactionRepository transactionRepository;
	
	@Mock
	TransactionDetailRepository transactionDetailRepository;
	
	@Mock
	TransactionToPaymentContextMapper transactionToPaymentContextMapper;
	
	@Mock
	PaymentContextToPaymentResponseMapper paymentContextToPaymentResponseMapper;
	
	@InjectMocks
	GetTransactionStatusFlowHandler getTransactionStatusFlowHandler;
	
	List<PaymentContext> paymentContextList;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		paymentContextList = new ArrayList<PaymentContext>();
		paymentContextList.add(setUpPaymentContext(PaymentMethodEnum.CARD));
		getTransactionStatusFlowHandler.setPaymentContextList(paymentContextList);
	}

	@Test
	final void testProcess_Exception() {
		Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.empty());
		
		assertThrows(TransactionNotFoundException.class, () -> getTransactionStatusFlowHandler.process());
	}
	
	@Test
	final void testProcess_Success() {
		Transaction transaction = new Transaction();
		Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.ofNullable(transaction));
		
		List<TransactionDetails> txnDetails = new ArrayList<TransactionDetails>();
		txnDetails.add(new TransactionDetails());
		Mockito.when(transactionDetailRepository.findTransactionDetailsByPaymentId(anyString())).thenReturn(txnDetails);
		
		getTransactionStatusFlowHandler.process();
	}
	
	@Test
	final void testProcess_TransactionDetails_Not_Present() {
		Transaction transaction = new Transaction();
		Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.ofNullable(transaction));
		
		Mockito.when(transactionDetailRepository.findTransactionDetailsByPaymentId(anyString())).thenReturn(null);
		
		getTransactionStatusFlowHandler.process();
	}

	
	private PaymentContext setUpPaymentContext(PaymentMethodEnum paymentMethod) {
		PaymentContext context = new PaymentContext();
		context.setPaymentId("ABCDEF");
		context.setPaymentInstructionContext(new PaymentInstructionContext());
		context.getPaymentInstructionContext().setPaymentMethod(paymentMethod);
		switch (paymentMethod) {
		case CARD:
			context.getPaymentInstructionContext().setBackingPaymentMethodContext(new CardPaymentContext());
			break;
		}
		return context;
	}

}
