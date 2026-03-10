package com.fooddeliveryapp.model.payment;

import com.fooddeliveryapp.enums.PaymentMethod;

import java.math.BigDecimal;

    public class PaymentProcessor {

        public boolean process(PaymentMethod method, BigDecimal amount) {

            IPaymentStrategy strategy =
                    PaymentFactory.getStrategy(method);

            return strategy.pay(amount);
        }
    }

