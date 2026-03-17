package com.fooddeliveryapp.service;

import com.fooddeliveryapp.enums.PaymentMethod;
import com.fooddeliveryapp.model.payment.IPaymentStrategy;
import com.fooddeliveryapp.model.payment.PaymentFactory;
import com.fooddeliveryapp.model.payment.PaymentResult;

import java.math.BigDecimal;

public class PaymentProcessor {

    public PaymentResult process(PaymentMethod method, BigDecimal amount) {
        IPaymentStrategy strategy = PaymentFactory.getStrategy(method);
        return strategy.pay(amount);
    }
}