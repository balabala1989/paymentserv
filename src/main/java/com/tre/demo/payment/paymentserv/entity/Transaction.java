package com.tre.demo.payment.paymentserv.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name="transaction")
@Getter
@Setter
public class Transaction {

	@Id
	private String paymentId;
	
	@Column
	private LocalDateTime timeCreated;
	
	@Column
	private String status;
	
	@Column
	private String debugId;
}
