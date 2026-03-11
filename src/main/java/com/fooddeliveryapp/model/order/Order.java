package com.fooddeliveryapp.model.order;

import com.fooddeliveryapp.enums.OrderStatus;
import com.fooddeliveryapp.model.user.Address;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private int deliveryAgentId;
    private BigDecimal totalAmount;
    private int addressId;
    private OrderStatus orderStatus;
    private double discountRate;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private List<OrderItem> orderItems;
    private static int counter = 0;

    public Order(int customerId, int deliveryAgentId, BigDecimal totalAmount, int addressId, OrderStatus orderStatus, List<OrderItem> orderItems) {
        this.id = ++counter;
        this.customerId = customerId;
        this.deliveryAgentId = deliveryAgentId;
        this.totalAmount = totalAmount;
        this.addressId = addressId;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getDeliveryAgentId() {
        return deliveryAgentId;
    }

    public void setDeliveryAgentId(int deliveryAgentId) {
        this.deliveryAgentId = deliveryAgentId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Order.counter = counter;
    }

    public void addItem(OrderItem orderItem)
    {
        orderItems.add(orderItem);
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }
}
