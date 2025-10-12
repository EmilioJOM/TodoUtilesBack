package com.uade.tpo.demo.controllers.cart;

import com.uade.tpo.demo.entity.Cart;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.CartProducts;
import com.uade.tpo.demo.entity.dto.CartResponseDTO;
import com.uade.tpo.demo.entity.dto.CartProductResponseDTO;
import com.uade.tpo.demo.entity.dto.MessageResponseDTO;
import com.uade.tpo.demo.exceptions.EmptyCartException;
import com.uade.tpo.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("carts")
public class CartController {

    @Autowired
    private CartService cartService;




    // Obtener o crear el carrito activo
    @GetMapping("/cart")
    public ResponseEntity<CartResponseDTO> getOrCreateActiveCart(@AuthenticationPrincipal User user) {
        System.out.println("GET: carts/cart");
        Cart cart = cartService.getOrCreateActiveCart(user);

        CartResponseDTO response = new CartResponseDTO(
                cart.getId(),
                cart.getSubtotal(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );

        return ResponseEntity.ok(response);
    }

    // Obtener productos del carrito activo
    @GetMapping("/products")
    public ResponseEntity<List<CartProductResponseDTO>> getCartProducts(@AuthenticationPrincipal User user) {
        System.out.println("GET: carts/products");
        List<CartProducts> cartProducts = cartService.getActiveCartProducts(user);

        if (cartProducts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CartProductResponseDTO> response = cartProducts.stream()
                .map(cp -> new CartProductResponseDTO(
                        cp.getProduct().getDescription(),
                        cp.getQuantity()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // Agregar producto al carrito
    @PostMapping("/add/{productId}")
    public ResponseEntity<MessageResponseDTO> addProduct(@AuthenticationPrincipal User user,
                                                         @PathVariable Long productId,
                                                         @RequestParam int quantity) {
        System.out.println("GET: carts/add/"+productId.toString());
        try {
            cartService.addProductToCart(user, productId, quantity);
            return ResponseEntity.created(URI.create("/carts/cart"))
                    .body(new MessageResponseDTO("Producto agregado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponseDTO("No se pudo agregar por: " + e.getMessage()));
        }
    }

    // Editar cantidad de un producto en el carrito
    @PutMapping("/update/{productId}")
    public ResponseEntity<MessageResponseDTO> updateProduct(@AuthenticationPrincipal User user,
                                                            @PathVariable Long productId,
                                                            @RequestParam int quantity) {
        System.out.println("PUT: carts/update/"+productId.toString());
        try {
            cartService.updateProductQuantity(user, productId, quantity);
            return ResponseEntity.ok(new MessageResponseDTO("Carrito actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponseDTO("No se pudo actualizar el carrito por: " + e.getMessage()));
        }
    }

    // Quitar un producto del carrito
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<MessageResponseDTO> removeProduct(@AuthenticationPrincipal User user,
                                                            @PathVariable Long productId) {
        System.out.println("DELETE: carts/remove/"+productId.toString());
        try {
            cartService.removeProductFromCart(user, productId);
            return ResponseEntity.ok(new MessageResponseDTO("El producto fue eliminado del carrito correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponseDTO("El producto no está en el carrito"));
        }
    }

    // Comprar el carrito
    @PostMapping("/purchase")
    public ResponseEntity<MessageResponseDTO> purchaseCart(@AuthenticationPrincipal User user) throws EmptyCartException {
        System.out.println("POST: carts/purchase");
        cartService.purchaseCart(user);
        return ResponseEntity.ok(new MessageResponseDTO("Se confirmó el carrito de compras correctamente"));
    }
}


