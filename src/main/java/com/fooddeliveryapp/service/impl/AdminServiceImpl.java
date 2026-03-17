package com.fooddeliveryapp.service.impl;

import com.fooddeliveryapp.model.discount.Discount;
import com.fooddeliveryapp.model.menu.MenuCategory;
import com.fooddeliveryapp.model.menu.MenuItem;
import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.model.user.Customer;
import com.fooddeliveryapp.model.user.DeliveryAgent;
import com.fooddeliveryapp.repository.dbimpl.DeliveryAgentRepository;
import com.fooddeliveryapp.repository.dbimpl.DiscountRepository;
import com.fooddeliveryapp.repository.dbimpl.MenuRepository;
import com.fooddeliveryapp.repository.dbimpl.OrderRepository;
import com.fooddeliveryapp.repository.dbimpl.UserRepository;
import com.fooddeliveryapp.service.AuthService;
import com.fooddeliveryapp.service.IAdminService;

import java.math.BigDecimal;
import java.util.List;

public class AdminServiceImpl implements IAdminService {

    private final MenuRepository menuRepository;
    private final DeliveryAgentRepository deliveryAgentRepository;
    private final OrderRepository orderRepository;
    private final DiscountRepository discountRepository;
    private final AuthService authService;

    public AdminServiceImpl(MenuRepository menuRepository, DeliveryAgentRepository deliveryAgentRepository, OrderRepository orderRepository, DiscountRepository discountRepository, AuthService authService) {
        this.menuRepository = menuRepository;
        this.deliveryAgentRepository = deliveryAgentRepository;
        this.orderRepository = orderRepository;
        this.discountRepository = discountRepository;
        this.authService = authService;
    }

    @Override
    public void addMenuItem(MenuItem item, int categoryId) {
        if (categoryId <= 0) throw new IllegalArgumentException("Invalid category ID");
        if (item.getName() == null || item.getName().isBlank())
            throw new IllegalArgumentException("Item name cannot be empty");
        if (item.getPrice() == null || item.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Price must be greater than 0");
        menuRepository.addMenuItem(item, categoryId);
    }

    @Override
    public void removeMenuItem(int menuItemId) {
        if (menuItemId <= 0) throw new IllegalArgumentException("Invalid menu item ID");
        menuRepository.removeMenuItem(menuItemId);
    }

    @Override
    public void addMenuCategory(MenuCategory category) {
        if (category.getName() == null || category.getName().isBlank())
            throw new IllegalArgumentException("Category name cannot be empty");
        menuRepository.addMenuCategory(category);
    }

    @Override
    public void removeMenuCategory(int categoryId) {
        if (categoryId <= 0) throw new IllegalArgumentException("Invalid category ID");
        menuRepository.removeMenuCategory(categoryId);
    }

    @Override
    public void addDeliveryAgent(DeliveryAgent agent) {
        authService.registerDeliveryAgent(agent.getName(), agent.getEmail(), agent.getPhone(), agent.getPassword());
    }

    @Override
    public void removeDeliveryAgent(int agentId) {
        if (agentId <= 0) throw new IllegalArgumentException("Invalid agent ID");
        deliveryAgentRepository.removeAgent(agentId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return UserRepository.getAllUsers(com.fooddeliveryapp.enums.Role.CUSTOMER).stream().map(u -> (Customer) u).toList();
    }

    @Override
    public void setDiscount(String name, BigDecimal minAmount, int rate) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Discount name cannot be empty");
        if (minAmount == null || minAmount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Minimum amount cannot be negative");
        if (rate < 0 || rate > 100) throw new IllegalArgumentException("Discount rate must be between 0 and 100");
        Discount discount = new Discount(name, minAmount, rate);
        discountRepository.saveDiscount(discount);
    }

    @Override
    public Discount getActiveDiscount() {
        return discountRepository.getActiveDiscount();
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuRepository.getAllMenuItems();
    }

    @Override
    public List<MenuCategory> getAllMenuCategories() {
        return menuRepository.getAllMenuCategories();
    }

    @Override
    public List<Order> getUnassignedOrders() {
        return orderRepository.getPendingUnassignedOrders();
    }

    @Override
    public List<DeliveryAgent> getAvailableAgents() {
        return deliveryAgentRepository.getAllDeliveryAgent().stream().filter(DeliveryAgent::isAvailable).toList();
    }

    @Override
    public void manualAssignAgent(int orderId, int agentId) {
        if (orderId <= 0) throw new IllegalArgumentException("Invalid order ID");
        if (agentId <= 0) throw new IllegalArgumentException("Invalid agent ID");
        orderRepository.assignDeliveryAgent(orderId, agentId);
        deliveryAgentRepository.setAvailability(agentId, false);
    }

    @Override
    public List<DeliveryAgent> getAllDeliveryAgents() {
        return deliveryAgentRepository.getAllDeliveryAgent();
    }
}