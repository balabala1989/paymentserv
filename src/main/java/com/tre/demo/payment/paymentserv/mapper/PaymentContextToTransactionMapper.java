package com.tre.demo.payment.paymentserv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.tre.demo.payment.paymentserv.context.PaymentContext;
import com.tre.demo.payment.paymentserv.entity.Transaction;

@Mapper(componentModel = "spring")
public interface PaymentContextToTransactionMapper {

	
	@Mappings({
		@Mapping(target = "paymentId", source = "paymentId"),
		@Mapping(target = "timeCreated", source = "timeStamp"),
		@Mapping(target = "debugId", source = "debugId")
	})
	public Transaction mapPaymentContextToTransaction(PaymentContext context);
}
