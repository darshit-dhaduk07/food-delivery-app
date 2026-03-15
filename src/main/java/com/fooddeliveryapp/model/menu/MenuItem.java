package com.fooddeliveryapp.model.menu;

import java.math.BigDecimal;

public class MenuItem{
    private int id;
    private BigDecimal price;
    private String name;
    private boolean isAvailable;

    public MenuItem(BigDecimal price,String name)
    {
        this.price = price;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
