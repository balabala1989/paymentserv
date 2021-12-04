package com.tre.demo.payment.paymentserv.api.model.method;

import com.tre.demo.payment.paymentserv.api.model.BackingPaymentMethod;

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
public class CardPaymentMethod extends BackingPaymentMethod {

	private String cardNumber;
	private String cvv;
	private int expiryMonth;
	private int expiryYear;
	private String cardNetwork;

}
