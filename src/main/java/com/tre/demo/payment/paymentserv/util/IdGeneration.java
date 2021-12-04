package com.tre.demo.payment.paymentserv.util;

import java.security.SecureRandom;
import java.util.Random;

public class IdGeneration {
	
	private final static Random random = new SecureRandom();
	
	public static String generateId(int size) {
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < size; i++)
			builder.append(PaymentConstants.CHARACTERS.charAt(random.nextInt(PaymentConstants.CHARACTERS.length())));
		
		return builder.toString();
	}
}
