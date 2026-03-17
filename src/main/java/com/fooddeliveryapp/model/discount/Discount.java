package com.fooddeliveryapp.model.discount;

import java.math.BigDecimal;

public class Discount {
    private int id;
    private String name;
    private BigDecimal minAmount;
    private int rate;
    private boolean isActive;

    public Discount() {
        this.name = "No Discount";
        this.minAmount = BigDecimal.ZERO;
        this.rate = 0;
    }

    public Discount(String name, BigDecimal minAmount, int rate) {
        this.name = name;
        this.minAmount = minAmount;
        this.rate = rate;
        this.isActive = true;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getMinAmount() { return minAmount; }
    public void setMinAmount(BigDecimal minAmount) { this.minAmount = minAmount; }

    public int getRate() { return rate; }
    public void setRate(int rate) { this.rate = rate; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}