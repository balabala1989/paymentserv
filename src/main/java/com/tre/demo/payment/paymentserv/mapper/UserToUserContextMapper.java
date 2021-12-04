package com.tre.demo.payment.paymentserv.mapper;

import org.mapstruct.Mapper;

import com.tre.demo.payment.paymentserv.api.model.Address;
import com.tre.demo.payment.paymentserv.api.model.User;
import com.tre.demo.payment.paymentserv.context.AddressContext;
import com.tre.demo.payment.paymentserv.context.UserContext;

@Mapper(componentModel = "spring")
public interface UserToUserContextMapper {
	
	public AddressContext mapAddressToAddressContext(Address address);

	public UserContext mapUserToUserContext(User user);
}
