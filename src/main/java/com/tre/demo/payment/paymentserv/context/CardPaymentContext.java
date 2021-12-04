package com.tre.demo.payment.paymentserv.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardPaymentContext extends BackingPaymentMethodContext {
	
	private String cardNumber;
	private String cvv;
	private int expiryMonth;
	private int expiryYear;
	private String cardNetwork;
	private String cardProduct;
	private String authCode;

}
