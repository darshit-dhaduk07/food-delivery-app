package com.fooddeliveryapp.model.menu;

import java.math.BigDecimal;

public class MenuItem extends MenuComponent{
    private int id;
    private BigDecimal price;
    private String name;
    private boolean isAvailable;
    private static int counter = 0;

    public MenuItem(BigDecimal price,String name)
    {
        this.id = ++counter;
        this.price = price;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
