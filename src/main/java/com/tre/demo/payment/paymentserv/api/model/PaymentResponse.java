package com.tre.demo.payment.paymentserv.api.model;

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
public class PaymentResponse {

	private String paymentId;
	private PaymentStatus paymentStatus;
	private String getStatusUrl;
	private String debugId;
	
}
