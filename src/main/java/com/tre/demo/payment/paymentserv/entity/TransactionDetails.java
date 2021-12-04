package com.tre.demo.payment.paymentserv.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="transaction_detail")
@Getter
@Setter
public class TransactionDetails {

	@Id
	private String internalTransactionId;
	
	@Column
	private String paymentId;
	
	@Column
	private String paymentMethod;
	
	@Column
	private String vendorTransactionId;
	
	@Column
	private String status;
	
	@Column
	private LocalDateTime timeCreated;
	
	@Column
	private Double toAmount;
	
	@Column
	private String toCurrency;
	
	@Column
	private Double fromAmount;
	
	@Column
	private String fromCurrency;
	
	@Column
	private Double fxRate;
	
	@Column
	private boolean currencyConverted;
}
