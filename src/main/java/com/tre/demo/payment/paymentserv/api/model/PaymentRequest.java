package com.tre.demo.payment.paymentserv.api.model;

import java.time.LocalDateTime;
import java.util.List;

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
public class PaymentRequest {

	private String paymentId;
	private User user;
	private boolean isSplitPayment;
	private boolean isPartialPaymentAllowed;
	private LocalDateTime timeStamp;
	private List<PaymentInstruction> instructions;
	
}
