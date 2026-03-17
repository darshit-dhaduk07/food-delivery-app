package com.fooddeliveryapp.model.payment;

import java.math.BigDecimal;

public class CashPayment implements IPaymentStrategy {

    @Override
    public PaymentResult pay(BigDecimal amount) {
        System.out.println("Cash payment of ₹" + amount + " to be collected on delivery");
        return new PaymentResult(true, "Cash payment of ₹" + amount + " will be collected on delivery");
    }
}
