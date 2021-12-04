package com.tre.demo.payment.paymentserv.context;

import java.time.LocalDateTime;

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
public class PaymentContext {

	private String paymentId;
	private UserContext userContext;
	private boolean isSplitPayment;
	private boolean isPartialPaymentAllowed;
	private LocalDateTime timeStamp;
	private PaymentInstructionContext paymentInstructionContext;
	private String debugId;
	private PaymentStatus paymentStatus;

}
