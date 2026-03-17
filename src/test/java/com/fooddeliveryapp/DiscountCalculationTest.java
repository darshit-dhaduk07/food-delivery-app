package com.fooddeliveryapp;

import com.fooddeliveryapp.model.discount.Discount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountCalculationTest {

    @Test
    void defaultDiscount_shouldHaveZeroRate() {
        Discount d = new Discount();
        assertEquals(0, d.getRate());
        assertEquals(BigDecimal.ZERO, d.getMinAmount());
    }

    @Test
    void discount_shouldApplyCorrectly() {
        // 10% off on order above 500
        BigDecimal total = new BigDecimal("640.00");
        Discount discount = new Discount("Sale", new BigDecimal("500"), 10);

        // eligible
        assertTrue(total.compareTo(discount.getMinAmount()) >= 0);

        BigDecimal discountAmount = total
                .multiply(BigDecimal.valueOf(discount.getRate() / 100.0));
        BigDecimal finalAmount = total.subtract(discountAmount);

        assertEquals(0, finalAmount.compareTo(new BigDecimal("576.0")));
    }

    @Test
    void discount_shouldNotApply_whenBelowMinAmount() {
        BigDecimal total = new BigDecimal("200.00");
        Discount discount = new Discount("Sale", new BigDecimal("500"), 10);

        // not eligible
        assertFalse(total.compareTo(discount.getMinAmount()) >= 0);
    }
}