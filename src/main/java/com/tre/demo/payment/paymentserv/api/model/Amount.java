package com.tre.demo.payment.paymentserv.api.model;

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
public class Amount {

	private Currency fromCurrency;
	private double fromAmount;
	private boolean isConversionRequired;
	private double fxRate;
	private Currency toCurrency;
	private double toAmount;

}
