package com.tre.demo.payment.paymentserv.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.tre.demo.payment.paymentserv.api.model.Error;
import com.tre.demo.payment.paymentserv.exceptions.PaymentFailureException;
import com.tre.demo.payment.paymentserv.exceptions.TransactionNotFoundException;

@RunWith(MockitoJUnitRunner.class)
class PaymentExceptionBuilderTest {
	
	@Mock
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	PaymentExceptionBuilder builder;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		builder = new PaymentExceptionBuilder();
	}

	@Test
	final void testHandle_PaymentFailure_Exception() {
		ResponseEntity entity = builder.handle(new PaymentFailureException("Test Message"), request, response);
		assertTrue(entity.getBody() instanceof Error);
		Error error = (Error) entity.getBody();
		assertEquals(error.getName(), "Payment Processing Failure");
		assertEquals(error.getMessage(), "Test Message");
	}
	
	@Test
	final void testHandle_TransactionNotFound_Exception() {
		ResponseEntity entity = builder.handle(new TransactionNotFoundException("Test Message"), request, response);
		assertTrue(entity.getBody() instanceof Error);
		Error error = (Error) entity.getBody();
		assertEquals(error.getName(), "Resource Not Found!!!");
		assertEquals(error.getMessage(), "Test Message");
	}
	
	@Test
	final void testHandle_Other_Exception() {
		ResponseEntity entity = builder.handle(new RuntimeException("Test Message"), request, response);
		assertNull(entity.getBody());
	}

}
