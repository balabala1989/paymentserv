package com.tre.demo.payment.paymentserv.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.tre.demo.payment.paymentserv.api.model.Amount;
import com.tre.demo.payment.paymentserv.api.model.PaymentInstruction;
import com.tre.demo.payment.paymentserv.api.model.PaymentRequest;
import com.tre.demo.payment.paymentserv.api.model.enums.PaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.BankPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.CardPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.PayPalPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.StripePaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.SwishPaymentMethod;
import com.tre.demo.payment.paymentserv.context.AmountContext;
import com.tre.demo.payment.paymentserv.context.BankPaymentContext;
import com.tre.demo.payment.paymentserv.context.CardPaymentContext;
import com.tre.demo.payment.paymentserv.context.PayPalPaymentContext;
import com.tre.demo.payment.paymentserv.context.PaymentMethodEnum;
import com.tre.demo.payment.paymentserv.context.StripePaymentContext;
import com.tre.demo.payment.paymentserv.context.SwishPaymentContext;
import com.tre.demo.payment.paymentserv.context.UserContext;
import com.tre.demo.payment.paymentserv.mapper.PaymentInstructionToPaymentinstructionContextMapper;
import com.tre.demo.payment.paymentserv.mapper.UserToUserContextMapper;


@RunWith(MockitoJUnitRunner.class)
class PaymentContextBuilderTest {
	
	@Mock
	UserToUserContextMapper userToUserContextMapper;
	
	@Mock
	PaymentInstructionToPaymentinstructionContextMapper paymentInstructionMapper;
	
	@InjectMocks
	PaymentContextBuilder contextBuilder;
	
	PaymentRequest request;

	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		request = new PaymentRequest();
		Mockito.when(userToUserContextMapper.mapUserToUserContext(any())).thenReturn(new UserContext());
		Mockito.when(paymentInstructionMapper.mapCardPaymentMethodToCardPaymentContext(any())).thenReturn(new CardPaymentContext());
		Mockito.when(paymentInstructionMapper.mapBankPaymentMethodToBankPaymentContext(any())).thenReturn(new BankPaymentContext());
		Mockito.when(paymentInstructionMapper.mapSwishPaymentMethodToSwishPaymentContext(any())).thenReturn(new SwishPaymentContext());
		Mockito.when(paymentInstructionMapper.mapStripePaymentMethodToStripePaymentContext(any())).thenReturn(new StripePaymentContext());
		Mockito.when(paymentInstructionMapper.mapPayPalPaymentMethodToPayPalPaymentContext(any())).thenReturn(new PayPalPaymentContext());
		Mockito.when(paymentInstructionMapper.mapAmountToAmountContext(any())).thenReturn(new AmountContext());
		Mockito.when(paymentInstructionMapper.mapPaymentMethodToPaymentMethodEnum(any())).thenReturn(PaymentMethodEnum.BANK);
	}

	@Test
	final void testBuild_Success_Multiple_Instruction() {
		List<PaymentInstruction> list = new ArrayList<PaymentInstruction>();
		list.add(new PaymentInstruction(new CardPaymentMethod(), new Amount(), PaymentMethod.CARD));
		list.add(new PaymentInstruction(new BankPaymentMethod(), new Amount(), PaymentMethod.BANK));
		list.add(new PaymentInstruction(new PayPalPaymentMethod(), new Amount(), PaymentMethod.PAYPAL));
		list.add(new PaymentInstruction(new SwishPaymentMethod(), new Amount(), PaymentMethod.SWISH));
		list.add(new PaymentInstruction(new StripePaymentMethod(), new Amount(), PaymentMethod.STRIPE));
		request.setInstructions(list);
		
		assertEquals(contextBuilder.build(request).size(), 5);
	}
	
	@Test
	final void testBuild_Success_Empty_Request() {
		assertTrue(contextBuilder.build(null).isEmpty());
	}
	
	@Test
	final void testBuild_Success_One_Instruction() {
		List<PaymentInstruction> list = new ArrayList<PaymentInstruction>();
		list.add(new PaymentInstruction(new CardPaymentMethod(), new Amount(), PaymentMethod.CARD));
		request.setInstructions(list);
		
		assertEquals(contextBuilder.build(request).size(), 1);
		
	}

}
