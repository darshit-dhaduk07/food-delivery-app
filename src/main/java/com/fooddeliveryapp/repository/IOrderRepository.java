package com.fooddeliveryapp.repository;

import com.fooddeliveryapp.model.order.Order;

import java.util.List;

public interface IOrderRepository {
    List<Order> findOrdersByUserId(int id);
    void save(Order order,int id);
    List<Order> findAllPendingOrders();

    Order takePendingOrder();
}
