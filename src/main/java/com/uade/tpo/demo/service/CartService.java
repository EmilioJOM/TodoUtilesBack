package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Cart;
import com.uade.tpo.demo.entity.CartProducts;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.User;

import java.util.List;

public interface CartService {
    Cart getOrCreateActiveCart(User user);

    List<Product> getProductsFromActiveCart(User user);

    Cart addProductToCart(User user, Long productId, int quantity);

    Cart updateProductQuantity(User user, Long productId, int quantity);

    Cart removeProductFromCart(User user, Long productId);

    // CORREGIDO: Quitar 'throws EmptyCartException'
    Cart purchaseCart(User user);

    public List<CartProducts> getActiveCartProducts(User user);
}