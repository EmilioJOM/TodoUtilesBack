package com.uade.tpo.demo.controllers.products;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.exceptions.CategoryNonexistentException;
import com.uade.tpo.demo.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/productos")
public class ProductController {

    @Autowired
    private ProductService productService;
 
    // Crear producto
    @PostMapping
    public Product createProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request.getDescripcion(), request.getStock(), request.getPrice());
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public Boolean eraseProduct(@PathVariable Long id) {
        return productService.eraseProduct(id);
    }

    // Agregar categoría
    @PostMapping("/add-category")
    public Product addProductCategory(@RequestParam long id, @RequestParam String categoryDescription)
            throws CategoryNonexistentException {
        return productService.addProductCategory(id, categoryDescription);
    }

    // Quitar categoría
    @PostMapping("/delete-category")
    public Product deleteProductCategory(@RequestParam long id, @RequestParam String categoryDescription)
            throws CategoryNonexistentException {
        return productService.deleteProductCategory(id, categoryDescription);
    }

    // Cambiar descripción
    @PostMapping("/change-description")
    public Product changeDescription(@RequestParam long id, @RequestParam String description) {
        return productService.changeDescription(id, description);
    }

    // Agregar stock
    @PostMapping("/add-stock")
    public Product addStock(@RequestParam long id, @RequestParam int stock) {
        return productService.addStock(id, stock);
    }

    // Cambiar precio
    @PostMapping("/change-price")
    public Product changePrice(@RequestParam long id, @RequestParam double price) {
        return productService.changePrice(id, price);
    }

    // Obtener producto
    @GetMapping("/{id}")
    public Product obtainProduct(@PathVariable Long id) {
        return productService.obtainProduct(id);
    }


}
