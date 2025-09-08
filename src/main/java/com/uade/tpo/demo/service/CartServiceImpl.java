package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.*;
import com.uade.tpo.demo.exceptions.EmptyCartException;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    return cartRepository.findActiveCart(user.getId())
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setState(true);
                newCart.setSubtotal(0.0); 
                newCart.setCartProducts(new ArrayList<>()); 
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
        if (quantity < 0) throw new RuntimeException("Cantidad inválida");
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
@Transactional(rollbackFor = Throwable.class)
public Cart purchaseCart(User user) throws EmptyCartException {
    Cart cart = getOrCreateActiveCart(user);

    if (cart.getCartProducts() == null || cart.getCartProducts().isEmpty()) {
        throw new EmptyCartException();
    }

    // 🔎 Verificar stock actualizado
    Map<String, Integer> insufficientStock = new HashMap<>();
    for (CartProducts cp : cart.getCartProducts()) {
        Product product = productRepository.findById(cp.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (cp.getQuantity() > product.getStock()) {
            insufficientStock.put(product.getDescription(), product.getStock());
        }
    }

    if (!insufficientStock.isEmpty()) {
        throw new InsufficientStockException(insufficientStock);
    }

    // ✅ Descontar stock
    for (CartProducts cp : cart.getCartProducts()) {
        Product product = cp.getProduct();
        product.setStock(product.getStock() - cp.getQuantity());
        productRepository.save(product);
    }

    // ✅ Cambiar estado a inactivo
    cart.setState(false);
    updateCartSubtotal(cart);
    cartRepository.save(cart);

    // ✅ Crear nuevo carrito vacío
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
