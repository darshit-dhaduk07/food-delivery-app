package com.fooddeliveryapp.model.payment;

import java.math.BigDecimal;

public class UpiPayment implements IPaymentStrategy {

    @Override
    public PaymentResult pay(BigDecimal amount) {
        System.out.println("Processing UPI payment of ₹" + amount + "...");
        return new PaymentResult(true, "UPI payment of ₹" + amount + " successful");
    }
}