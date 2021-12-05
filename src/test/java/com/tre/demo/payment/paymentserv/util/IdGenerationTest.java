package com.tre.demo.payment.paymentserv.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IdGenerationTest {

	@Test
	final void testGenerateId_Success() {
		assertEquals(IdGeneration.generateId(10).length(), 10);
	}
	
	@Test
	final void testGenerateId_Success_Empty() {
		assertEquals(IdGeneration.generateId(0).length(), 0);
	}

}
