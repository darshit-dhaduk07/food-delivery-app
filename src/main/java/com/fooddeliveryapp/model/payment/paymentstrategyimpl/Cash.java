package com.fooddeliveryapp.model.payment.paymentstrategyimpl;

import com.fooddeliveryapp.model.payment.IPaymentStrategy;

import java.math.BigDecimal;

public class Cash implements IPaymentStrategy {

    @Override
    public boolean pay(BigDecimal amount) {
        System.out.println("Paid via Cash: ₹" + amount);
        return true;
    }
}
