package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Product;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    
    public Product createProduct(String description, int stock, double price);

    public Boolean eraseProduct(Long id);

    // QUITAR throws CategoryNonexistentException
    public Product addProductCategory(long id, String categoryDescription);

    // QUITAR throws CategoryNonexistentException  
    public Product deleteProductCategory(long id, String categoryDescription);

    public Product changeDescription(long id, String description);

    public Product addStock(long id, int stock);

    public List<Product> getAllProducts();

    public Product obtainProduct(Long id);

    public Product changePrice(long id, double price);

    Product subirImagen(Long id, MultipartFile file);
}