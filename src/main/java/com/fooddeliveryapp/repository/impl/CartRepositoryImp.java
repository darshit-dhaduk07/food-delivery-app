package com.fooddeliveryapp.repository.impl;

import com.fooddeliveryapp.model.cart.Cart;
import com.fooddeliveryapp.repository.ICartRepository;

import java.util.HashMap;
import java.util.Map;

public class CartRepositoryImp implements ICartRepository {

    private final Map<Integer,Cart> cartMap;
    private ICartRepository cartRepository;

    private CartRepositoryImp() {
        cartMap = new HashMap<>();
    }

    public ICartRepository getCartRepository() {
        if(cartRepository == null)
            cartRepository = new CartRepositoryImp();
        return cartRepository;
    }

    @Override
    public Cart getCartByUserId(int id) {
        return cartMap.get(id);
    }

    @Override
    public void add(Cart cart, int id) {
        cartMap.put(id,cart);
    }

    @Override
    public void removeCart(int id) {
        cartMap.remove(id);
    }
}
