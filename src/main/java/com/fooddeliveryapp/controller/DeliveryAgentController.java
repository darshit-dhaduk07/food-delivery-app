package com.fooddeliveryapp.controller;

import com.fooddeliveryapp.enums.OrderStatus;
import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.service.IDeliveryAgentService;

import java.util.List;

public class DeliveryAgentController {

    private final IDeliveryAgentService deliveryAgentService;

    public DeliveryAgentController(IDeliveryAgentService deliveryAgentService) {
        this.deliveryAgentService = deliveryAgentService;
    }

    public List<Order> viewAssignedOrders(int agentId) {
        return deliveryAgentService.viewAssignedOrders(agentId);
    }

    public void updateOrderStatus(int agentId, int orderId, OrderStatus status) {
        deliveryAgentService.updateOrderStatus(agentId, orderId, status);
    }

    public int viewEarnings(int agentId) {
        return deliveryAgentService.viewEarnings(agentId);
    }
}