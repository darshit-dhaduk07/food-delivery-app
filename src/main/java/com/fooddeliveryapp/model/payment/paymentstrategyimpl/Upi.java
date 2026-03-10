package com.fooddeliveryapp.model.payment.paymentstrategyimpl;

import com.fooddeliveryapp.model.payment.IPaymentStrategy;

import java.math.BigDecimal;

public class Upi implements IPaymentStrategy
{
    @Override
    public boolean pay(BigDecimal amount) {
        System.out.println("Paid via UPI: ₹" + amount);
        return true;
    }
}
