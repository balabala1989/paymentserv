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
public class PayPalPaymentMethod extends BackingPaymentMethod{
	
	private String userId;

}
