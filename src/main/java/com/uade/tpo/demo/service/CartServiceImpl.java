package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.*;
import com.uade.tpo.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartProductsRepository cartProductsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart getOrCreateActiveCart(User user) {
        return cartRepository.findByUserAndStateTrue(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setState(true);
                    newCart.setSubtotal(0);
                    return cartRepository.save(newCart);
                });
    }

    @Override
    public List<Product> getProductsFromActiveCart(User user) {
        Cart cart = getOrCreateActiveCart(user);
        return cart.getCartProducts().stream()
                .map(CartProducts::getProduct)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Cart addProductToCart(User user, Long productId, int quantity) {
        Cart cart = getOrCreateActiveCart(user);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Stock insuficiente");
        }

        CartProducts cartProduct = cartProductsRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseGet(() -> {
                    CartProducts cp = new CartProducts();
                    cp.setCart(cart);
                    cp.setProduct(product);
                    cp.setQuantity(0);
                    cart.getCartProducts().add(cp);
                    return cp;
                });

        cartProduct.setQuantity(cartProduct.getQuantity() + quantity);

        if (cartProduct.getQuantity() > product.getStock()) {
            throw new RuntimeException("No puedes superar el stock disponible");
        }

        cartProductsRepository.save(cartProduct);
        updateCartSubtotal(cart);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    @Transactional
    public Cart updateProductQuantity(User user, Long productId, int quantity) {
        if (quantity < 0) throw new RuntimeException("Cantidad inválida");

        Cart cart = getOrCreateActiveCart(user);
        CartProducts cartProduct = cartProductsRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("El producto no está en el carrito"));

        if (quantity > cartProduct.getProduct().getStock()) {
            throw new RuntimeException("Cantidad supera el stock");
        }

        cartProduct.setQuantity(quantity);
        cartProductsRepository.save(cartProduct);
        updateCartSubtotal(cart);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    @Transactional
    public Cart removeProductFromCart(User user, Long productId) {
        Cart cart = getOrCreateActiveCart(user);
        CartProducts cartProduct = cartProductsRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("El producto no está en el carrito"));

        cart.getCartProducts().remove(cartProduct);
        cartProductsRepository.delete(cartProduct);
        updateCartSubtotal(cart);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    @Transactional
    public Cart purchaseCart(User user) {
        Cart cart = getOrCreateActiveCart(user);

        // Cambiar estado a inactivo
        cart.setState(false);
        updateCartSubtotal(cart); // opcional, mantiene el subtotal final
        cartRepository.save(cart);

        // Crear nuevo carrito vacío
        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setState(true);
        newCart.setSubtotal(0);
        return cartRepository.save(newCart);
    }

    public List<CartProducts> getActiveCartProducts(User user) {
        Cart cart = getOrCreateActiveCart(user);
        return cart.getCartProducts();
    }
    // Método privado para actualizar el subtotal
    
    private void updateCartSubtotal(Cart cart) {
        double subtotal = cart.getCartProducts().stream()
                .mapToDouble(cp -> cp.getProduct().getPrice() * cp.getQuantity())
                .sum();
        cart.setSubtotal(subtotal);
    }
}
