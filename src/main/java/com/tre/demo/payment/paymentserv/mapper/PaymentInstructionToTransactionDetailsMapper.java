package com.tre.demo.payment.paymentserv.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.tre.demo.payment.paymentserv.context.PaymentContext;
import com.tre.demo.payment.paymentserv.context.PaymentInstructionContext;
import com.tre.demo.payment.paymentserv.entity.Transaction;
import com.tre.demo.payment.paymentserv.entity.TransactionDetails;

@Mapper(componentModel = "spring")
public interface PaymentInstructionToTransactionDetailsMapper {

	@Mappings({
		@Mapping(target = "internalTransactionId", source = "backingPaymentMethodContext.internalTransactionId"),
		@Mapping(target = "vendorTransactionId", source = "backingPaymentMethodContext.vendorTransactionId"),
		@Mapping(target = "toAmount", source = "amountContext.toAmount"),
		@Mapping(target = "toCurrency", source = "amountContext.toCurrency"),
		@Mapping(target = "fromAmount", source = "amountContext.fromAmount"),
		@Mapping(target = "fromCurrency", source = "amountContext.fromCurrency"),
		@Mapping(target = "fxRate", source = "amountContext.fxRate"),
		@Mapping(target = "currencyConverted", source = "amountContext.conversionRequired"),
		@Mapping(target = "status", source = "paymentStatus"),
		@Mapping(target = "paymentMethod", source = "paymentMethod")
	})
	public TransactionDetails mapPaymentContextToTransaction(PaymentInstructionContext paymentInstructionContext);
}
