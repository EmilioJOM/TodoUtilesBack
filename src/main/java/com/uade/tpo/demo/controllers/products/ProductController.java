package com.uade.tpo.demo.controllers.products;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductController {

    @Autowired
    private ProductService productService;
  
    @PostMapping
    public Product createProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request.getDescripcion(), request.getStock(), request.getPrice());
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product p) {
        return productService.updateProduct(id, p);
    }

    @DeleteMapping("/{id}")
    public void eraseProduct(@PathVariable Long id) {
        productService.eraseProduct(id);
    }
}
