package com.fooddeliveryapp;

import com.fooddeliveryapp.enums.PaymentMethod;
import com.fooddeliveryapp.model.payment.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    void paymentFactory_shouldReturnUpiStrategy() {
        IPaymentStrategy strategy = PaymentFactory.getStrategy(PaymentMethod.UPI);
        assertInstanceOf(UpiPayment.class, strategy);
    }

    @Test
    void paymentFactory_shouldReturnCashStrategy() {
        IPaymentStrategy strategy = PaymentFactory.getStrategy(PaymentMethod.CASH);
        assertInstanceOf(CashPayment.class, strategy);
    }

    @Test
    void upiPayment_shouldReturnSuccess() {
        IPaymentStrategy upi = new UpiPayment();
        PaymentResult result = upi.pay(new BigDecimal("500"));
        assertTrue(result.isSuccess());
        assertNotNull(result.getMessage());
    }

    @Test
    void cashPayment_shouldReturnSuccess() {
        IPaymentStrategy cash = new CashPayment();
        PaymentResult result = cash.pay(new BigDecimal("300"));
        assertTrue(result.isSuccess());
        assertNotNull(result.getMessage());
    }

    @Test
    void paymentResult_success_shouldReturnTrue() {
        PaymentResult result = new PaymentResult(true, "Payment done");
        assertTrue(result.isSuccess());
        assertEquals("Payment done", result.getMessage());
    }

    @Test
    void paymentResult_failure_shouldReturnFalse() {
        PaymentResult result = new PaymentResult(false, "Payment failed");
        assertFalse(result.isSuccess());
    }
}
