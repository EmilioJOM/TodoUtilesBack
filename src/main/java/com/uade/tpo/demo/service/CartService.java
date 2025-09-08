package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Cart;

import com.uade.tpo.demo.entity.CartProducts;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.exceptions.EmptyCartException;

import java.util.List;

public interface CartService {
    Cart getOrCreateActiveCart(User user);

    List<Product> getProductsFromActiveCart(User user);

    Cart addProductToCart(User user, Long productId, int quantity);

    Cart updateProductQuantity(User user, Long productId, int quantity);

    Cart removeProductFromCart(User user, Long productId);

    Cart purchaseCart(User user) throws EmptyCartException;

    public List<CartProducts> getActiveCartProducts(User user);
}
