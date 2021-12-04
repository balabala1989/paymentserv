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
public class Address {

	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	
}
