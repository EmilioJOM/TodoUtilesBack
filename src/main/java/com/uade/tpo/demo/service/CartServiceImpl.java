package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.*;
import com.uade.tpo.demo.exceptions.EmptyCartException;
import com.uade.tpo.demo.exceptions.InsufficientStockException;
import com.uade.tpo.demo.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartProductsRepository cartProductsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public Cart getOrCreateActiveCart(User user) {
        return cartRepository.findActiveCart(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setState("ACTIVE");
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
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        
        Cart cart = getOrCreateActiveCart(user);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Stock insuficiente para el producto ID: " + productId);
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

        int newQuantity = cartProduct.getQuantity() + quantity;

        if (newQuantity > product.getStock()) {
            throw new RuntimeException("No puedes superar el stock disponible para el producto ID: " + productId);
        }

        cartProduct.setQuantity(newQuantity);
        cartProductsRepository.save(cartProduct);
        updateCartSubtotal(cart);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    @Transactional
    public Cart updateProductQuantity(User user, Long productId, int quantity) {
        Cart cart = getOrCreateActiveCart(user);
        CartProducts cartProduct = cartProductsRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("El producto no está en el carrito"));

        if (quantity == 0) {
            throw new IllegalArgumentException("La cantidad no puede ser cero");
        }

        int newQuantity = cartProduct.getQuantity() + quantity;

        if (newQuantity < 0) {
            throw new IllegalArgumentException("No se puede tener cantidad negativa en el carrito");
        }

        if (quantity > 0 && newQuantity > cartProduct.getProduct().getStock()) {
            throw new RuntimeException("Stock insuficiente para el producto ID: " + productId);
        }

        if (newQuantity == 0) {
            cart.getCartProducts().remove(cartProduct);
            cartProductsRepository.delete(cartProduct);
        } else {
            cartProduct.setQuantity(newQuantity);
            cartProductsRepository.save(cartProduct);
        }

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
    public Cart purchaseCart(User user) {
        Cart cart = getOrCreateActiveCart(user);

        Optional<Cart> pendingCart = cartRepository.findPendingCartByUser(user.getId());
        if (pendingCart.isPresent()) {
            throw new RuntimeException("No se puede confirmar el carrito: ya existe una compra pendiente");
        }

        // USA EmptyCartException (debería funcionar después del cambio)
        if (cart.getCartProducts() == null || cart.getCartProducts().isEmpty()) {
            throw new EmptyCartException(); // Constructor sin parámetros
        }

        // Prepara el Map para InsufficientStockException
        Map<String, Integer> insufficientStock = new HashMap<>();
        for (CartProducts cp : cart.getCartProducts()) {
            Product product = productRepository.findById(cp.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (cp.getQuantity() > product.getStock()) {
                insufficientStock.put("Producto ID: " + product.getId(), product.getStock());
            }
        }

        if (!insufficientStock.isEmpty()) {
            throw new InsufficientStockException(insufficientStock); // Constructor con Map
        }

        // Actualizar stock
        for (CartProducts cp : cart.getCartProducts()) {
            Product product = cp.getProduct();
            product.setStock(product.getStock() - cp.getQuantity());
            productRepository.save(product);
        }

        cart.setState("PENDING");
        updateCartSubtotal(cart);
        cartRepository.save(cart);

        Venta venta = new Venta();
        venta.setIdUsuario(user.getId());
        venta.setCart(cart);
        venta.setTotal(cart.getSubtotal());
        venta.setFecha(LocalDateTime.now());
        venta.setEstado("PENDING");
        ventaRepository.save(venta);

        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setState("ACTIVE");
        newCart.setSubtotal(0.0);
        newCart.setCartProducts(new ArrayList<>());
        return cartRepository.save(newCart);
    }

    public List<CartProducts> getActiveCartProducts(User user) {
        Cart cart = getOrCreateActiveCart(user);
        return cart.getCartProducts();
    }
    
    private void updateCartSubtotal(Cart cart) {
        double subtotal = cart.getCartProducts().stream()
                .mapToDouble(cp -> cp.getProduct().getPrice() * cp.getQuantity())
                .sum();
        cart.setSubtotal(subtotal);
    }
}