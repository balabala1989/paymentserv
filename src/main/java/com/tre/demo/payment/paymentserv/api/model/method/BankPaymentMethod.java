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
public class BankPaymentMethod extends BackingPaymentMethod {

	private String iban;
	private String branchCode;
	private String bankName;
	private String routingNumber;
	private String bban;
	private String swiftCode;

}
