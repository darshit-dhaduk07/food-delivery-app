package com.fooddeliveryapp.service;

import com.fooddeliveryapp.enums.PaymentMethod;
import com.fooddeliveryapp.model.order.Order;

public interface IOrderService {
    Order placeOrder(int customerId, int addressId, PaymentMethod paymentMethod);
    void cancelOrder(int orderId);
}