package com.fooddeliveryapp.model.payment;

import com.fooddeliveryapp.enums.PaymentMethod;

public class PaymentFactory {

    public static IPaymentStrategy getStrategy(PaymentMethod method) {
        return switch (method) {
            case UPI -> new UpiPayment();
            case CASH -> new CashPayment();
        };
    }
}