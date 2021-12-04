package com.tre.demo.payment.paymentserv.util;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tre.demo.payment.paymentserv.api.model.PaymentInstruction;
import com.tre.demo.payment.paymentserv.api.model.PaymentRequest;
import com.tre.demo.payment.paymentserv.api.model.method.BankPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.CardPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.PayPalPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.StripePaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.SwishPaymentMethod;
import com.tre.demo.payment.paymentserv.context.PaymentContext;
import com.tre.demo.payment.paymentserv.context.PaymentInstructionContext;
import com.tre.demo.payment.paymentserv.mapper.PaymentInstructionToPaymentinstructionContextMapper;
import com.tre.demo.payment.paymentserv.mapper.UserToUserContextMapper;

@Component
@Scope("prototype")
public class PaymentContextBuilder {

	public List<PaymentContext> build(PaymentRequest request) {
		
		UserToUserContextMapper userToUserContextMapper = Mappers.getMapper(UserToUserContextMapper.class);
		PaymentInstructionToPaymentinstructionContextMapper paymentInstructionMapper = Mappers.getMapper(PaymentInstructionToPaymentinstructionContextMapper.class);
		List<PaymentContext> paymentContextList = new ArrayList<PaymentContext>();
		
		
		for(PaymentInstruction instruction : request.getInstructions()) {
			PaymentContext paymentContext = new PaymentContext();
			paymentContext.setUserContext(userToUserContextMapper.mapUserToUserContext(request.getUser()));
			paymentContext.setPaymentId(request.getPaymentId());
			paymentContext.setSplitPayment(request.isSplitPayment());
			paymentContext.setPartialPaymentAllowed(request.isPartialPaymentAllowed());
			paymentContext.setTimeStamp(request.getTimeStamp());
			
			PaymentInstructionContext instructionContext = new PaymentInstructionContext();
			
			if (instruction.getBackingPaymentMethod() instanceof CardPaymentMethod )
				instructionContext.setBackingPaymentMethodContext(paymentInstructionMapper.mapCardPaymentMethodToCardPaymentContext((CardPaymentMethod) instruction.getBackingPaymentMethod()));
			else if (instruction.getBackingPaymentMethod() instanceof BankPaymentMethod)
				instructionContext.setBackingPaymentMethodContext(paymentInstructionMapper.mapBankPaymentMethodToBankPaymentContext((BankPaymentMethod) instruction.getBackingPaymentMethod()));
			else if (instruction.getBackingPaymentMethod() instanceof SwishPaymentMethod)
				instructionContext.setBackingPaymentMethodContext(paymentInstructionMapper.mapSwishPaymentMethodToSwishPaymentContext((SwishPaymentMethod) instruction.getBackingPaymentMethod()));
			else if (instruction.getBackingPaymentMethod() instanceof StripePaymentMethod)
				instructionContext.setBackingPaymentMethodContext(paymentInstructionMapper.mapStripePaymentMethodToStripePaymentContext((StripePaymentMethod) instruction.getBackingPaymentMethod()));
			else if (instruction.getBackingPaymentMethod() instanceof PayPalPaymentMethod)
				instructionContext.setBackingPaymentMethodContext(paymentInstructionMapper.mapPayPalPaymentMethodToPayPalPaymentContext((PayPalPaymentMethod) instruction.getBackingPaymentMethod()));
			
			
			
			instructionContext.setAmountContext(paymentInstructionMapper.mapAmountToAmountContext(instruction.getAmount()));
			instructionContext.setPaymentMethod(paymentInstructionMapper.mapPaymentMethodToPaymentMethodEnum(instruction.getPaymentMethod()));
			
			paymentContext.setPaymentInstructionContext(instructionContext);
			
			paymentContextList.add(paymentContext);
		}
		
		return paymentContextList;
	}
}
