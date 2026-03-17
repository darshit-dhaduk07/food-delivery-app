package com.fooddeliveryapp.service;

import com.fooddeliveryapp.enums.OrderStatus;
import com.fooddeliveryapp.model.order.Order;

import java.util.List;

public interface IDeliveryAgentService {
    List<Order> viewAssignedOrders(int agentId);
    void updateOrderStatus(int agentId, int orderId, OrderStatus status);
    int viewEarnings(int agentId);
}