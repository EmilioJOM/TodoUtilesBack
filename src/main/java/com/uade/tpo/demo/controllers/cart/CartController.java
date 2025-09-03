package com.uade.tpo.demo.controllers.cart;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("Cart") //localhost:4002/Cart

public class CartController {


    //Obtener carrito
    @GetMapping("/{userId}") //localhost:4002/Cart/1
    public String getCartByUserId(@PathVariable String userId) {
        return new String();
    }

    //Crear carrito
    @PostMapping() //localhost:4002/Cart
    public String createCart(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
    //Agregar productos al carrito
    @PutMapping("/{cartId}/products/{productId}") //localhost:4002/Cart/1/products/10
    public String addProductToCart(@PathVariable String cartId, @PathVariable String productId, @RequestBody String entity) {
        //TODO: process PUT request
        
        return entity;
    }

    //Modificar elemento del carrito
    @PutMapping("/{cartId}}") //localhost:4002/Cart/1
    public String updateCart(@PathVariable String cartId, @RequestBody String entity) {
        //TODO: process PUT request
        
        return entity;
    }
 
    //Comprar carrito
    @PutMapping("/{cartId}}") //localhost:4002/Cart/1
    public String buyCart(@PathVariable String cartId, @RequestBody String entity) {
        //TODO: process PUT request
        
        return entity;
    }

    //Sacar un producto del carrito
    @DeleteMapping("/{cartId}/products/{productId}") //localhost:4002/Cart/2/products/9
    public String deleteProductFromCart(@PathVariable String cartId, @PathVariable String productId) {
        
    return "Producto " + productId + " eliminado del carrito " + cartId;
}

}
