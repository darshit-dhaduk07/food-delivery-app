package com.fooddeliveryapp.model.payment;

import java.math.BigDecimal;

public interface IPaymentStrategy {
    PaymentResult pay(BigDecimal amount);
}