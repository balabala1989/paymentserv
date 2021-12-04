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
public class UserContext {

	private String firstName;
	private String lastName;
	private AddressContext address;
	private String email;
	private String phone;

}
