package com.uade.tpo.demo.controllers;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // Filtros: ?q=texto  |  ?categoriaId=1
    @GetMapping
    public List<Product> listar(@RequestParam(required = false) String q,
                                @RequestParam(required = false) Long categoriaId) {
        return service.listar(q, categoriaId);
    }

    @GetMapping("/{id}")
    public Product obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    public Product crear(@RequestBody Product p) {
        return service.crear(p);
    }

    @PutMapping("/{id}")
    public Product actualizar(@PathVariable Long id, @RequestBody Product p) {
        return service.actualizar(id, p);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        service.borrar(id);
    }
}
