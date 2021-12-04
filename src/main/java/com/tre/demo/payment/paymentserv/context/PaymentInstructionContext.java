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
public class PaymentInstructionContext {

	private BackingPaymentMethodContext backingPaymentMethodContext;
	private AmountContext amountContext;
	private PaymentMethodEnum paymentMethod;
	private PaymentStatus paymentStatus;
}
