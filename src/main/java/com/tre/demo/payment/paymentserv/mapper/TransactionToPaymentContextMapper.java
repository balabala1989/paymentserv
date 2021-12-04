package com.tre.demo.payment.paymentserv.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.tre.demo.payment.paymentserv.api.model.BackingPaymentMethod;
import com.tre.demo.payment.paymentserv.context.AmountContext;
import com.tre.demo.payment.paymentserv.context.BackingPaymentMethodContext;
import com.tre.demo.payment.paymentserv.context.BankPaymentContext;
import com.tre.demo.payment.paymentserv.context.CardPaymentContext;
import com.tre.demo.payment.paymentserv.context.PayPalPaymentContext;
import com.tre.demo.payment.paymentserv.context.PaymentContext;
import com.tre.demo.payment.paymentserv.context.PaymentInstructionContext;
import com.tre.demo.payment.paymentserv.context.PaymentMethodEnum;
import com.tre.demo.payment.paymentserv.context.StripePaymentContext;
import com.tre.demo.payment.paymentserv.context.SwishPaymentContext;
import com.tre.demo.payment.paymentserv.entity.Transaction;
import com.tre.demo.payment.paymentserv.entity.TransactionDetails;

@Mapper(componentModel = "spring")
public interface TransactionToPaymentContextMapper {

	@Mappings({
		@Mapping(target = "paymentId", source = "paymentId"),
		@Mapping(target = "paymentStatus", source = "status"),
		@Mapping(target = "debugId", source = "debugId")
	})
	public PaymentContext mapTransactionToPaymentContext(Transaction transaction);
	
	
	public List<PaymentInstructionContext> mapTransactionDetailsToPaymentContextList(List<TransactionDetails> transactionDetails);
	
	@Mappings({
		@Mapping(target = "backingPaymentMethodContext", source = "transactionDetails", qualifiedByName = "mapInternalId"),
		@Mapping(target = "paymentMethod", source = "paymentMethod"),
		@Mapping(target = "amountContext", source = "transactionDetails", qualifiedByName = "mapTransactionDetailsAmountToAmountContext"),
		@Mapping(target = "paymentStatus", source = "status")
	})
	public PaymentInstructionContext mapTransactionDetailsToPaymentContext(TransactionDetails transactionDetails);
	
	
	@Mappings({
		@Mapping(target = "fromCurrency", source = "fromCurrency"),
		@Mapping(target = "fromAmount", source = "fromAmount"),
		@Mapping(target = "toCurrency", source = "toCurrency"),
		@Mapping(target = "toAmount", source = "toAmount"),
		@Mapping(target = "fxRate", source = "fxRate"),
		@Mapping(target = "isConversionRequired", source = "currencyConverted"),
	})
	public AmountContext mapTransactionDetailsAmountToAmountContext(TransactionDetails transactionDetails);
	
	
	
	@Named("mapInternalId")
	public default BackingPaymentMethodContext mapInternalId(TransactionDetails transactionDetails) {
		
		if (transactionDetails.getPaymentMethod() == PaymentMethodEnum.CARD.name()) {
			CardPaymentContext cardPaymentContext = new CardPaymentContext();
			cardPaymentContext.setInternalTransactionId(transactionDetails.getInternalTransactionId());
			cardPaymentContext.setVendorTransactionId(transactionDetails.getVendorTransactionId());
			return cardPaymentContext;
		}
		else if (transactionDetails.getPaymentMethod() == PaymentMethodEnum.BANK.name()) {
			BankPaymentContext bankPaymentContext = new BankPaymentContext();
			bankPaymentContext.setInternalTransactionId(transactionDetails.getInternalTransactionId());
			bankPaymentContext.setVendorTransactionId(transactionDetails.getVendorTransactionId());
			return bankPaymentContext;
		}
		else if (transactionDetails.getPaymentMethod() == PaymentMethodEnum.PAYPAL.name()) {
			PayPalPaymentContext payPalPaymentContext = new PayPalPaymentContext();
			payPalPaymentContext.setInternalTransactionId(transactionDetails.getInternalTransactionId());
			payPalPaymentContext.setVendorTransactionId(transactionDetails.getVendorTransactionId());
			return payPalPaymentContext;
		}
		else if (transactionDetails.getPaymentMethod() == PaymentMethodEnum.SWISH.name()) {
			SwishPaymentContext swishPaymentContext = new SwishPaymentContext();
			swishPaymentContext.setInternalTransactionId(transactionDetails.getInternalTransactionId());
			swishPaymentContext.setVendorTransactionId(transactionDetails.getVendorTransactionId());
			return swishPaymentContext;
		}
		else if (transactionDetails.getPaymentMethod() == PaymentMethodEnum.STRIPE.name()) {
			StripePaymentContext stripePaymentContext = new StripePaymentContext();
			stripePaymentContext.setInternalTransactionId(transactionDetails.getInternalTransactionId());
			stripePaymentContext.setVendorTransactionId(transactionDetails.getVendorTransactionId());
			return stripePaymentContext;
		}
		return null;
		
	}
	
	
}
