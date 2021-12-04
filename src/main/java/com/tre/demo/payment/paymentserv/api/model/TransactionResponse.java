package com.tre.demo.payment.paymentserv.api.model;

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
public class TransactionResponse{
	
	private PaymentResponse paymentResponse;
	private List<PayemntInstructionStatus> instructionStatus;
	
}
