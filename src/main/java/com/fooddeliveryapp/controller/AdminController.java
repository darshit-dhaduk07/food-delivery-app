package com.fooddeliveryapp.controller;

import com.fooddeliveryapp.model.discount.Discount;
import com.fooddeliveryapp.model.menu.MenuCategory;
import com.fooddeliveryapp.model.menu.MenuItem;
import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.model.user.Customer;
import com.fooddeliveryapp.model.user.DeliveryAgent;
import com.fooddeliveryapp.service.IAdminService;

import java.math.BigDecimal;
import java.util.List;

public class AdminController {

    private final IAdminService adminService;

    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    public void addMenuItem(String name, BigDecimal price, int categoryId) {
        MenuItem item = new MenuItem(price, name);
        item.setAvailable(true);
        adminService.addMenuItem(item, categoryId);
    }

    public void removeMenuItem(int menuItemId) {
        adminService.removeMenuItem(menuItemId);
    }

    public void addMenuCategory(String name) {
        MenuCategory category = new MenuCategory(name);
        adminService.addMenuCategory(category);
    }

    public void removeMenuCategory(int categoryId) {
        adminService.removeMenuCategory(categoryId);
    }

    public void addDeliveryAgent(String name, String email,
                                 String phone, String password) {
        adminService.addDeliveryAgent(
                new com.fooddeliveryapp.model.user.DeliveryAgent(
                        name, email, phone, password,
                        com.fooddeliveryapp.enums.Role.DELIVERY_AGENT
                )
        );
    }

    public void removeDeliveryAgent(int agentId) {
        adminService.removeDeliveryAgent(agentId);
    }

    public List<Order> getAllOrders() {
        return adminService.getAllOrders();
    }

    public List<Customer> getAllCustomers() {
        return adminService.getAllCustomers();
    }

    public void setDiscount(String name, BigDecimal minAmount, int rate) {
        adminService.setDiscount(name, minAmount, rate);
    }

    public Discount getActiveDiscount() {
        return adminService.getActiveDiscount();
    }
    public List<MenuItem> getAllMenuItems() {
        return adminService.getAllMenuItems();
    }

    public List<MenuCategory> getAllMenuCategories() {
        return adminService.getAllMenuCategories();
    }
    public List<Order> getUnassignedOrders() {
        return adminService.getUnassignedOrders();
    }

    public List<DeliveryAgent> getAvailableAgents() {
        return adminService.getAvailableAgents();
    }

    public void manualAssignAgent(int orderId, int agentId) {
        adminService.manualAssignAgent(orderId, agentId);
    }

    public List<DeliveryAgent> getAllDeliveryAgents() {
        return adminService.getAllDeliveryAgents();
    }
}