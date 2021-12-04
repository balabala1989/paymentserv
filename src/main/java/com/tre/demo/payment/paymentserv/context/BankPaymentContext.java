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
public class BankPaymentContext extends BackingPaymentMethodContext{

	private String iban;
	private String branchCode;
	private String bankName;
	private String routingNumber;
	private String bban;
	private String swiftCode;
}
