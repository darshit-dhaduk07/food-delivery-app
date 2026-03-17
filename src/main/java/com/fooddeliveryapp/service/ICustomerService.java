package com.fooddeliveryapp.service;

import com.fooddeliveryapp.enums.PaymentMethod;
import com.fooddeliveryapp.model.cart.CartItem;
import com.fooddeliveryapp.model.menu.MenuCategory;
import com.fooddeliveryapp.model.menu.MenuItem;
import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.model.user.Address;

import java.util.List;

public interface ICustomerService {
    List<MenuItem> getAvailableMenuItems();

    void addToCart(int customerId, int menuItemId, int quantity);
    void removeFromCart(int cartItemId);
    void updateCartQuantity(int cartItemId, int quantity);
    List<CartItem> viewCart(int customerId);

    void addAddress(int customerId, String addressName);
    List<Address> getAddresses(int customerId);

    Order placeOrder(int customerId, int addressId, PaymentMethod paymentMethod);
    List<Order> viewOrderHistory(int customerId);
    List<MenuCategory> getMenuCategories();
}