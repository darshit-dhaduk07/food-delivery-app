package com.fooddeliveryapp.model.discount;

import java.math.BigDecimal;

public class Discount {
    private String name ;
    private BigDecimal minAmount;
    private int rate;
    private Discount discount = new Discount("No Discount",new BigDecimal(0),0);

    private Discount(String name, BigDecimal minAmount, int rate) {
        this.name = name;
        this.minAmount = minAmount;
        this.rate = rate;
    }

    public Discount getInstance()
    {
        return discount;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public int getRate() {
        return rate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
