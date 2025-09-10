package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    public Product obtener(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Product createProduct(String description, int stock, double price){
        Product auxProd=new Product(description,stock,price);
        validarBasico(auxProd);
        productRepository.save(auxProd);
        return auxProd;
    }

    public Product updateProduct(Long id, Product p) {
        Product db = obtener(id);
        if (p.getDescription() != null)
            db.setDescription(p.getDescription());
        if (p.getPrice() > 0)
            db.setPrice(p.getPrice());
        if (p.getCategories() != null)
            db.setCategories(p.getCategories());
        validarBasico(db);
        return productRepository.save(db);
    }

    public void eraseProduct(Long id) {
        productRepository.deleteById(id);
    }


    private void validarBasico(Product p) {
        if (p.getDescription() == null || p.getDescription().isBlank()) {
            throw new RuntimeException("Descripción requerida.");
        }
        if (p.getPrice() <= 0) {
            throw new RuntimeException("Precio debe ser > 0.");
        }
        if (p.getStock() <0){
            throw new RuntimeException("No puede haber un stock negativo.");
        }
    }
}
