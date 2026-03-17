package com.fooddeliveryapp.controller;

import com.fooddeliveryapp.enums.PaymentMethod;
import com.fooddeliveryapp.model.cart.CartItem;
import com.fooddeliveryapp.model.menu.MenuCategory;
import com.fooddeliveryapp.model.menu.MenuItem;
import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.model.user.Address;
import com.fooddeliveryapp.service.ICustomerService;

import java.util.List;

public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    public List<MenuItem> getAvailableMenuItems() {
        return customerService.getAvailableMenuItems();
    }

    public void addToCart(int customerId, int menuItemId, int quantity) {
        customerService.addToCart(customerId, menuItemId, quantity);
    }

    public void removeFromCart(int cartItemId) {
        customerService.removeFromCart(cartItemId);
    }

    public void updateCartQuantity(int cartItemId, int quantity) {
        customerService.updateCartQuantity(cartItemId, quantity);
    }

    public List<CartItem> viewCart(int customerId) {
        return customerService.viewCart(customerId);
    }

    public void addAddress(int customerId, String addressName) {
        customerService.addAddress(customerId, addressName);
    }

    public List<Address> getAddresses(int customerId) {
        return customerService.getAddresses(customerId);
    }

    public Order placeOrder(int customerId, int addressId, PaymentMethod paymentMethod) {
        return customerService.placeOrder(customerId, addressId, paymentMethod);
    }

    public List<Order> viewOrderHistory(int customerId) {
        return customerService.viewOrderHistory(customerId);
    }
    public List<MenuCategory> getMenuCategories() {
        return customerService.getMenuCategories();
    }
}