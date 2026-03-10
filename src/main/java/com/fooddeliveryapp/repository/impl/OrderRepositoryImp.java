package com.fooddeliveryapp.repository.impl;

import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.repository.IOrderRepository;

import java.util.*;

public class OrderRepositoryImp implements IOrderRepository {
    private IOrderRepository orderRepository;
    private final Map<Integer,List<Order>> orderHistory;
    private Deque<Order> pendingOrders;

    private OrderRepositoryImp() {
        orderHistory = new HashMap<>();
        pendingOrders = new ArrayDeque<>();
    }

    @Override
    public List<Order> findOrdersByUserId(int id) {
        return orderHistory.get(id);
    }

    @Override
    public void save(Order order,int id) {
        orderHistory.computeIfAbsent(id, k -> new ArrayList<>()).add(order);
    }

    @Override
    public List<Order> findAllPendingOrders() {
        return pendingOrders.stream().toList();
    }

    @Override
    public Order takePendingOrder() {
        return pendingOrders.pollFirst();
    }
}
