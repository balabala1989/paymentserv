package com.tre.demo.payment.paymentserv.controller;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.tre.demo.payment.paymentserv.api.model.Address;
import com.tre.demo.payment.paymentserv.api.model.Amount;
import com.tre.demo.payment.paymentserv.api.model.PayemntInstructionStatus;
import com.tre.demo.payment.paymentserv.api.model.PaymentInstruction;
import com.tre.demo.payment.paymentserv.api.model.PaymentRequest;
import com.tre.demo.payment.paymentserv.api.model.PaymentResponse;
import com.tre.demo.payment.paymentserv.api.model.PaymentStatus;
import com.tre.demo.payment.paymentserv.api.model.TransactionResponse;
import com.tre.demo.payment.paymentserv.api.model.User;
import com.tre.demo.payment.paymentserv.api.model.enums.PaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.BankPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.CardPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.PayPalPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.StripePaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.SwishPaymentMethod;
import com.tre.demo.payment.paymentserv.context.PaymentContext;
import com.tre.demo.payment.paymentserv.handler.CompletePaymentFlowHandler;
import com.tre.demo.payment.paymentserv.handler.GetTransactionStatusFlowHandler;
import com.tre.demo.payment.paymentserv.util.PaymentConstants;
import com.tre.demo.payment.paymentserv.util.PaymentContextBuilder;
import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
class PaymentsControllerTest {

	@InjectMocks
	PaymentsController paymentsController;
	
	@Mock
	PaymentContextBuilder contextBuiler;

	@Mock
	CompletePaymentFlowHandler completePaymentFlowHandler;

	@Mock
	GetTransactionStatusFlowHandler getTransactionStatusFlowHandler;

	PaymentRequest request;

	@BeforeEach
	void setUp() throws Exception {
		request = new PaymentRequest();
		request.setPaymentId("ABCDEF1234");
		request.setPartialPaymentAllowed(false);
		request.setSplitPayment(false);
		request.setTimeStamp(LocalDateTime.now());
		List<PaymentInstruction> instructionList = new ArrayList<PaymentInstruction>();
		instructionList.add(setUpInstructionList(PaymentMethod.CARD));
		instructionList.add(setUpInstructionList(PaymentMethod.BANK));
		request.setInstructions(instructionList);
		request.setUser(setUpUser());
		MockitoAnnotations.initMocks(this);

	}

	@Test
	final void testCompletePayment_Success() {
		PaymentResponse response = new PaymentResponse();
		response.setPaymentId("ABCDEF1234");
		response.setPaymentStatus(PaymentStatus.SUCCESS);
		response.setGetStatusUrl(PaymentConstants.SERVER_HOST_ADDRESS + "ABCDEF1234");
		
		Mockito.when(completePaymentFlowHandler.buildResponse()).thenReturn(response);
		Mockito.when(contextBuiler.build(any())).thenReturn(new ArrayList<PaymentContext>());
		
		PaymentResponse serverResponse = paymentsController.completePayment(request);
		
		assertEquals(serverResponse.getPaymentId(), request.getPaymentId());
		assertEquals(serverResponse.getPaymentStatus(), PaymentStatus.SUCCESS);
		assertEquals(serverResponse.getGetStatusUrl(), PaymentConstants.SERVER_HOST_ADDRESS + request.getPaymentId());
		
	}
	
	@Test
	final void testCompletePayment_Failure() {
		PaymentResponse response = new PaymentResponse();
		response.setPaymentId("ABCDEF1234");
		response.setPaymentStatus(PaymentStatus.FAILED);
		response.setGetStatusUrl(PaymentConstants.SERVER_HOST_ADDRESS + "ABCDEF1234");
		
		Mockito.when(completePaymentFlowHandler.buildResponse()).thenReturn(response);
		Mockito.when(contextBuiler.build(any())).thenReturn(new ArrayList<PaymentContext>());
		
		PaymentResponse serverResponse = paymentsController.completePayment(request);
		
		assertEquals(serverResponse.getPaymentId(), request.getPaymentId());
		assertEquals(serverResponse.getPaymentStatus(), PaymentStatus.FAILED);
		assertEquals(serverResponse.getGetStatusUrl(), PaymentConstants.SERVER_HOST_ADDRESS + request.getPaymentId());
		
	}

	@Test
	final void testGetTransactionDetails_Failure() {
		TransactionResponse response = new TransactionResponse();
		response.setPaymentResponse(new PaymentResponse("ABCCDDFRG", PaymentStatus.FAILED, null, null));
		response.setInstructionStatus(null);
		Mockito.when(getTransactionStatusFlowHandler.buildResponse()).thenReturn(response);
		
		TransactionResponse serverResponse = paymentsController.getTransactionDetails("ABCCDDFRG");
		
		assertEquals(serverResponse.getPaymentResponse().getPaymentId(), "ABCCDDFRG");
		assertEquals(serverResponse.getPaymentResponse().getPaymentStatus(), PaymentStatus.FAILED);
		assertNull(serverResponse.getInstructionStatus());
	}
	
	@Test
	final void testGetTransactionDetails_Success() {
		TransactionResponse response = new TransactionResponse();
		response.setPaymentResponse(new PaymentResponse("ABCCDDFRG", PaymentStatus.SUCCESS, null, null));
		List<PayemntInstructionStatus> instructionStatusList = new ArrayList<PayemntInstructionStatus>();
		instructionStatusList.add(new PayemntInstructionStatus("SDsdfsdlJK123","dfkljIOUO876",setUpAmount(false),PaymentStatus.SUCCESS, PaymentMethod.CARD));
		instructionStatusList.add(new PayemntInstructionStatus("owekjnkjsdnfk","kjsldhf876hbj",setUpAmount(true),PaymentStatus.SUCCESS, PaymentMethod.BANK));
		
		response.setInstructionStatus(instructionStatusList);
		Mockito.when(getTransactionStatusFlowHandler.buildResponse()).thenReturn(response);
		
		TransactionResponse serverResponse = paymentsController.getTransactionDetails("ABCCDDFRG");
		
		assertEquals(serverResponse.getPaymentResponse().getPaymentId(), "ABCCDDFRG");
		assertEquals(serverResponse.getPaymentResponse().getPaymentStatus(), PaymentStatus.SUCCESS);
		assertEquals(serverResponse.getInstructionStatus().size(), 2);
	}

	private PaymentInstruction setUpInstructionList(PaymentMethod paymentMethod) {
		PaymentInstruction paymentInstruction = new PaymentInstruction();

		switch (paymentMethod) {
		case CARD: 
			paymentInstruction.setPaymentMethod(paymentMethod);
			paymentInstruction.setAmount(setUpAmount(true));
			paymentInstruction.setBackingPaymentMethod(new CardPaymentMethod("456789823737","123",4,2022,"VISA"));
			break;
		case BANK: 
			paymentInstruction.setPaymentMethod(paymentMethod);
			paymentInstruction.setAmount(setUpAmount(false));
			paymentInstruction.setBackingPaymentMethod(new BankPaymentMethod());
			break;
		case PAYPAL: 
			paymentInstruction.setPaymentMethod(paymentMethod);
			paymentInstruction.setAmount(setUpAmount(true));
			paymentInstruction.setBackingPaymentMethod(new PayPalPaymentMethod("test@paypal.com"));
			break;
		case SWISH: 
			paymentInstruction.setPaymentMethod(paymentMethod);
			paymentInstruction.setAmount(setUpAmount(false));
			paymentInstruction.setBackingPaymentMethod(new SwishPaymentMethod("test@swish.com"));
			break;
		case STRIPE: 
			paymentInstruction.setPaymentMethod(paymentMethod);
			paymentInstruction.setAmount(setUpAmount(true));
			paymentInstruction.setBackingPaymentMethod(new StripePaymentMethod("test@stripe.com"));
			break;
		}

		return paymentInstruction;
	}
	
	private Amount setUpAmount(boolean currencyConversion) {
		Amount amount = new Amount();
		
		if (currencyConversion) {
			amount.setConversionRequired(currencyConversion);
			amount.setFromAmount(100.00);
			amount.setFromCurrency(Currency.getInstance("SEK"));
			amount.setToAmount(300.00);
			amount.setToCurrency(Currency.getInstance("USD"));
			amount.setFxRate(3.0);
		}
		else {
			amount.setToAmount(300.00);
			amount.setToCurrency(Currency.getInstance("SEK"));
		}
		
		return amount;
	}
	
	private User setUpUser() {
		User user = new User();
		user.setFirstName("Test");
		user.setLastName("Account");
		user.setAddress(new Address("Address1", null, null, null, null, "Sweden", "103 11"));
		return user;
	}

}
