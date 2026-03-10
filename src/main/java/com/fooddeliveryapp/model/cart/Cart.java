package com.fooddeliveryapp.model.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<Integer,CartItem> cartItems;
    private int customerId;

    public Cart(int customerId) {
        cartItems = new HashMap<>();
        this.customerId = customerId;
    }

    public List<CartItem> getCartItems() {
        return cartItems.values().stream().toList();
    }

    public void addCartItem(CartItem cartItem) {
        cartItems.put(cartItem.getMenuItemId(),cartItem);
    }
    public void removeCartItem(int id)
    {
        cartItems.remove(id);
    }
    public int getCustomerId() {
        return customerId;
    }

}
