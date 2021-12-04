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
public class User {

	private String firstName;
	private String lastName;
	private Address address;
	private String email;
	private String phone;
	
}
