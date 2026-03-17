package com.fooddeliveryapp.service.impl;

import com.fooddeliveryapp.enums.PaymentMethod;
import com.fooddeliveryapp.model.cart.CartItem;
import com.fooddeliveryapp.model.menu.MenuCategory;
import com.fooddeliveryapp.model.menu.MenuItem;
import com.fooddeliveryapp.model.order.Order;
import com.fooddeliveryapp.model.user.Address;
import com.fooddeliveryapp.repository.dbimpl.CartRepository;
import com.fooddeliveryapp.repository.dbimpl.CustomerRepository;
import com.fooddeliveryapp.repository.dbimpl.MenuRepository;
import com.fooddeliveryapp.repository.dbimpl.OrderRepository;
import com.fooddeliveryapp.service.ICustomerService;
import com.fooddeliveryapp.service.IOrderService;

import java.util.List;

public class CustomerServiceImpl implements ICustomerService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final IOrderService orderService;

    public CustomerServiceImpl(CartRepository cartRepository, CustomerRepository customerRepository, MenuRepository menuRepository, OrderRepository orderRepository, IOrderService orderService) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.menuRepository = menuRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @Override
    public List<MenuItem> getAvailableMenuItems() {
        return menuRepository.getAvailableMenuItems();
    }

    @Override
    public void addToCart(int customerId, int menuItemId, int quantity) {
        if (customerId <= 0) throw new IllegalArgumentException("Invalid customer ID");
        if (menuItemId <= 0) throw new IllegalArgumentException("Invalid menu item ID");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be at least 1");

        int cartId = cartRepository.getCartIdByCustomerId(customerId);
        MenuItem item = menuRepository.getMenuItemById(menuItemId);

        CartItem cartItem = new CartItem(menuItemId, quantity);
        cartItem.setCartId(cartId);
        cartItem.setPrice(item.getPrice());
        cartRepository.addItemToCart(cartItem);
    }

    @Override
    public void removeFromCart(int cartItemId) {
        if (cartItemId <= 0) throw new IllegalArgumentException("Invalid cart item ID");
        cartRepository.removeItemFromCart(cartItemId);
    }

    @Override
    public void updateCartQuantity(int cartItemId, int quantity) {
        if (cartItemId <= 0) throw new IllegalArgumentException("Invalid cart item ID");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be at least 1");
        cartRepository.updateQuantity(quantity, cartItemId);
    }

    @Override
    public List<CartItem> viewCart(int customerId) {
        if (customerId <= 0) throw new IllegalArgumentException("Invalid customer ID");
        return cartRepository.getAllCartItem(customerId);
    }

    @Override
    public void addAddress(int customerId, String addressName) {
        if (customerId <= 0) throw new IllegalArgumentException("Invalid customer ID");
        if (addressName == null || addressName.isBlank()) throw new IllegalArgumentException("Address cannot be empty");
        Address address = new Address(addressName, customerId);
        customerRepository.addAddress(address);
    }

    @Override
    public List<Address> getAddresses(int customerId) {
        if (customerId <= 0) throw new IllegalArgumentException("Invalid customer ID");
        return customerRepository.getAddressesByCustomerId(customerId);
    }

    @Override
    public Order placeOrder(int customerId, int addressId, PaymentMethod paymentMethod) {
        if (customerId <= 0) throw new IllegalArgumentException("Invalid customer ID");
        if (addressId <= 0) throw new IllegalArgumentException("Invalid address ID");
        if (paymentMethod == null) throw new IllegalArgumentException("Payment method is required");
        return orderService.placeOrder(customerId, addressId, paymentMethod);
    }

    @Override
    public List<Order> viewOrderHistory(int customerId) {
        if (customerId <= 0) throw new IllegalArgumentException("Invalid customer ID");
        return orderRepository.getOrdersByCustomerId(customerId);
    }

    @Override
    public List<MenuCategory> getMenuCategories() {
        return menuRepository.getAllMenuCategories();
    }

}