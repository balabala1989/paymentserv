package com.tre.demo.payment.paymentserv.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.tre.demo.payment.paymentserv.api.model.Amount;
import com.tre.demo.payment.paymentserv.api.model.PayemntInstructionStatus;
import com.tre.demo.payment.paymentserv.api.model.PaymentResponse;
import com.tre.demo.payment.paymentserv.context.AmountContext;
import com.tre.demo.payment.paymentserv.context.PaymentContext;
import com.tre.demo.payment.paymentserv.context.PaymentInstructionContext;
import com.tre.demo.payment.paymentserv.entity.TransactionDetails;

@Mapper(componentModel = "spring")
public interface PaymentContextToPaymentResponseMapper {

	@Mappings({
		@Mapping(target = "paymentId", source = "paymentId"),
		@Mapping(target = "paymentStatus", source = "paymentStatus"),
		@Mapping(target = "debugId", source = "debugId")
	})
	public PaymentResponse mapPaymentContextToPaymentResponse(PaymentContext paymentContext);
	
	public List<PayemntInstructionStatus> mapInstructionContextToStatusList(List<PaymentInstructionContext> instructionContext);
	
	@Mappings({
		@Mapping(target = "internalTransactionId", source = "backingPaymentMethodContext.internalTransactionId"),
		@Mapping(target = "vendorTransactionId", source = "backingPaymentMethodContext.vendorTransactionId"),
		@Mapping(target = "amount", source = "amountContext", qualifiedByName = "mapTransactionDetailsAmountToAmountContext"),
		@Mapping(target = "paymentMethod", source = "paymentMethod"),
		@Mapping(target = "paymentStatus", source = "paymentStatus")
	})
	public PayemntInstructionStatus mapInstructionContextToStatus(PaymentInstructionContext instructionContext);
	
	
	@Mappings({
		@Mapping(target = "fromCurrency", source = "fromCurrency"),
		@Mapping(target = "fromAmount", source = "fromAmount"),
		@Mapping(target = "toCurrency", source = "toCurrency"),
		@Mapping(target = "toAmount", source = "toAmount"),
		@Mapping(target = "fxRate", source = "fxRate"),
		@Mapping(target = "isConversionRequired", source = "conversionRequired"),
	})
	public Amount mapTransactionDetailsAmountToAmountContext(AmountContext amountContext);
}
