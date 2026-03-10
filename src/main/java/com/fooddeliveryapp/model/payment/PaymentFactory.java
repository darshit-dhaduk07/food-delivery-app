package com.fooddeliveryapp.model.payment;

import com.fooddeliveryapp.enums.PaymentMethod;
import com.fooddeliveryapp.model.payment.paymentstrategyimpl.Cash;
import com.fooddeliveryapp.model.payment.paymentstrategyimpl.Upi;

public class PaymentFactory {
    public static IPaymentStrategy getStrategy(PaymentMethod method) {

        return switch (method) {
            case UPI -> new Upi();
            case CASH -> new Cash();
        };
    }
}
