package com.tre.demo.payment.paymentserv.context;

import java.util.Currency;

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
public class AmountContext {
	
	private Currency fromCurrency;
	private double fromAmount;
	private Currency toCurrency;
	private double toAmount;
	private double fxRate;
	private boolean isConversionRequired;

}
