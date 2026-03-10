package com.fooddeliveryapp.repository;

import com.fooddeliveryapp.model.cart.Cart;

public interface ICartRepository {
    Cart getCartByUserId(int id);
    void add(Cart cart, int id);
    void removeCart(int id);
}
