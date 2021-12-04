package com.tre.demo.payment.paymentserv.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class BackingPaymentMethodContext {
	
	public String internalTransactionId;
	public String vendorTransactionId;

}
