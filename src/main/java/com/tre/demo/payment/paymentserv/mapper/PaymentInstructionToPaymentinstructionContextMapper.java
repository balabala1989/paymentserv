package com.tre.demo.payment.paymentserv.mapper;

import org.mapstruct.Mapper;

import com.tre.demo.payment.paymentserv.api.model.Amount;
import com.tre.demo.payment.paymentserv.api.model.PaymentInstruction;
import com.tre.demo.payment.paymentserv.api.model.enums.PaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.BankPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.CardPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.PayPalPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.StripePaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.SwishPaymentMethod;
import com.tre.demo.payment.paymentserv.context.AmountContext;
import com.tre.demo.payment.paymentserv.context.BankPaymentContext;
import com.tre.demo.payment.paymentserv.context.CardPaymentContext;
import com.tre.demo.payment.paymentserv.context.PayPalPaymentContext;
import com.tre.demo.payment.paymentserv.context.PaymentInstructionContext;
import com.tre.demo.payment.paymentserv.context.PaymentMethodEnum;
import com.tre.demo.payment.paymentserv.context.StripePaymentContext;
import com.tre.demo.payment.paymentserv.context.SwishPaymentContext;

@Mapper(componentModel = "spring")
public interface PaymentInstructionToPaymentinstructionContextMapper {

	CardPaymentContext mapCardPaymentMethodToCardPaymentContext(CardPaymentMethod cardPaymentMethod);
	
	BankPaymentContext mapBankPaymentMethodToBankPaymentContext(BankPaymentMethod bankPaymentMethod);
	
	PayPalPaymentContext mapPayPalPaymentMethodToPayPalPaymentContext(PayPalPaymentMethod payPalPaymentMethod);
	
	StripePaymentContext mapStripePaymentMethodToStripePaymentContext(StripePaymentMethod stripePaymentMethod);
	
	SwishPaymentContext mapSwishPaymentMethodToSwishPaymentContext(SwishPaymentMethod swishPaymentMethod);
	
	AmountContext mapAmountToAmountContext(Amount amount);
	
	PaymentMethodEnum mapPaymentMethodToPaymentMethodEnum(PaymentMethod paymentMethod);
	
	PaymentInstructionContext mapPaymentInstructionToPaymentInstructionContext(PaymentInstruction paymentInstruction);
	
}
