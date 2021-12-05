package com.tre.demo.payment.paymentserv.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import com.tre.demo.payment.paymentserv.processor.StripePaymentProcessor;
import com.tre.demo.payment.paymentserv.processor.SwishPaymentProcessor;
import com.tre.demo.payment.paymentserv.repository.TransactionDetailRepository;
import com.tre.demo.payment.paymentserv.repository.TransactionRepository;

@RunWith(MockitoJUnitRunner.class)
class CompletePaymentFlowHandlerTest {

	@Mock
	TransactionRepository transactionRepository;

	@Mock
	TransactionDetailRepository transactionDetailRepository;

	@Mock
	CardPaymentProcessor cardPaymentProcessor;

	@Mock
	BankPaymentProcessor bankPaymentProcessor;

	@Mock
	PayPalPaymentProcessor paypalPaymentProcessor;

	@Mock
	StripePaymentProcessor stripePaymentProcessor;

	@Mock
	SwishPaymentProcessor swishPaymentProcessor;
	
	@Mock
	PaymentContextToTransactionMapper paymenContextToTransactionMapper;
	
	@Mock
	PaymentInstructionToTransactionDetailsMapper payemntInstructionToTransactionDetailsMapper;
	
	@Mock
	TransactionToPaymentContextMapper transactionToPaymentContextMapper;
	
	@Mock
	PaymentContextToPaymentResponseMapper paymentContextToPaymentResponseMapper;
	

	@InjectMocks
	CompletePaymentFlowHandler completePaymentFlowHandler;
	
	List<PaymentContext> paymentContextList;

	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		paymentContextList = new ArrayList<PaymentContext>();
		paymentContextList.add(setUpPaymentContext(PaymentMethodEnum.CARD));
		completePaymentFlowHandler.setPaymentContextList(paymentContextList);
	}

	@Test
	final void testProcess_Transaction_AlreadyPresent() {
		Transaction transaction = new Transaction();
		Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.ofNullable(transaction));
		Mockito.when(paymenContextToTransactionMapper.mapPaymentContextToTransaction(any())).thenReturn(transaction);
		Mockito.when(transactionToPaymentContextMapper.mapTransactionToPaymentContext(any())).thenReturn(paymentContextList.get(0));
		completePaymentFlowHandler.process();
	}
	
	@Test
	final void testProcess_Success() {
		Transaction transaction = new Transaction();
		Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.empty()).thenReturn(Optional.ofNullable(transaction));
		Mockito.when(paymenContextToTransactionMapper.mapPaymentContextToTransaction(any())).thenReturn(transaction);
		Mockito.when(transactionToPaymentContextMapper.mapTransactionToPaymentContext(any())).thenReturn(paymentContextList.get(0));
		
		//CARD
		PaymentContext cardPaymentContext = setUpPaymentContext(PaymentMethodEnum.CARD);
		cardPaymentContext.getPaymentInstructionContext().setPaymentStatus(PaymentStatus.SUCCESS);
		Mockito.when(cardPaymentProcessor.execute()).thenReturn(cardPaymentContext);
		Mockito.when(payemntInstructionToTransactionDetailsMapper.mapPaymentContextToTransaction(any())).thenReturn(new TransactionDetails());
		
		completePaymentFlowHandler.process();
		
		List<PaymentContext> responseList = completePaymentFlowHandler.getPaymentContextList();
		assertEquals(responseList.size(), 1);
		assertEquals(responseList.get(0).getPaymentInstructionContext().getPaymentStatus(), PaymentStatus.SUCCESS);
	}
	
	@Test
	final void testProcess_Exception() {
		Transaction transaction = new Transaction();
		Mockito.when(transactionRepository.findById(anyString())).thenReturn(Optional.empty()).thenReturn(Optional.ofNullable(transaction));
		Mockito.when(paymenContextToTransactionMapper.mapPaymentContextToTransaction(any())).thenReturn(transaction);
		Mockito.when(transactionToPaymentContextMapper.mapTransactionToPaymentContext(any())).thenReturn(paymentContextList.get(0));
		
		//CARD
		PaymentContext cardPaymentContext = setUpPaymentContext(PaymentMethodEnum.CARD);
		cardPaymentContext.getPaymentInstructionContext().setPaymentStatus(PaymentStatus.FAILED);
		Mockito.when(cardPaymentProcessor.execute()).thenReturn(cardPaymentContext);
		Mockito.when(payemntInstructionToTransactionDetailsMapper.mapPaymentContextToTransaction(any())).thenReturn(new TransactionDetails());
		completePaymentFlowHandler.setPartialPaymentAllowed(false);
		
		assertThrows(PaymentFailureException.class, () -> completePaymentFlowHandler.process());
		
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
