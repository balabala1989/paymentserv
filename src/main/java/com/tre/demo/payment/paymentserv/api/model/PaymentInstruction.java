package com.tre.demo.payment.paymentserv.api.model;

import com.tre.demo.payment.paymentserv.api.model.enums.PaymentMethod;

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
public class PaymentInstruction {

	private BackingPaymentMethod backingPaymentMethod;
	private Amount amount;
	private PaymentMethod paymentMethod;

}
