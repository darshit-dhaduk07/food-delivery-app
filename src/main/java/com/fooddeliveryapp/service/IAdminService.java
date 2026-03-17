package com.fooddeliveryapp.service;

import com.fooddeliveryapp.model.discount.Discount;
import com.fooddeliveryapp.model.menu.MenuCategory;
import com.fooddeliveryapp.model.menu.MenuItem;
import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.model.user.Customer;
import com.fooddeliveryapp.model.user.DeliveryAgent;

import java.math.BigDecimal;
import java.util.List;

public interface IAdminService {
    void addMenuItem(MenuItem item, int categoryId);
    void removeMenuItem(int menuItemId);
    void addMenuCategory(MenuCategory category);
    void removeMenuCategory(int categoryId);
    void addDeliveryAgent(DeliveryAgent agent);
    void removeDeliveryAgent(int agentId);
    List<Order> getAllOrders();
    List<Customer> getAllCustomers();
    void setDiscount(String name, BigDecimal minAmount, int rate);
    Discount getActiveDiscount();
    List<MenuItem> getAllMenuItems();
    List<MenuCategory> getAllMenuCategories();
    List<Order> getUnassignedOrders();
    List<DeliveryAgent> getAvailableAgents();
    void manualAssignAgent(int orderId, int agentId);
    List<DeliveryAgent> getAllDeliveryAgents();
}