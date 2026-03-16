package com.fooddeliveryapp.model.order;

import java.math.BigDecimal;

public class OrderItem {
    private int id;
    private int orderId;
    private int menuItemId;
    private int quantity;
    private BigDecimal priceAtOrder;

    public OrderItem(int menuItemId, int quantity, BigDecimal priceAtOrder) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
    }

    public OrderItem() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getMenuItemId() { return menuItemId; }
    public void setMenuItemId(int menuItemId) { this.menuItemId = menuItemId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getPriceAtOrder() { return priceAtOrder; }
    public void setPriceAtOrder(BigDecimal priceAtOrder) { this.priceAtOrder = priceAtOrder; }
}