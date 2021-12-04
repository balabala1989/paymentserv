package com.tre.demo.payment.paymentserv.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tre.demo.payment.paymentserv.api.model.method.BankPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.CardPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.PayPalPaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.StripePaymentMethod;
import com.tre.demo.payment.paymentserv.api.model.method.SwishPaymentMethod;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CardPaymentMethod.class, name = "Card"),
    @JsonSubTypes.Type(value = BankPaymentMethod.class, name = "Bank"),
    @JsonSubTypes.Type(value = SwishPaymentMethod.class, name = "Swish"),
    @JsonSubTypes.Type(value = PayPalPaymentMethod.class, name = "PayPal"),
    @JsonSubTypes.Type(value = StripePaymentMethod.class, name = "Stripe")
    }
)
public abstract class BackingPaymentMethod {

}
