package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Product;

public interface ProductService {
    
    public Product createProduct(String description, int stock, double price);

    public Product updateProduct(Long id, Product p);

    public Boolean eraseProduct(Long id);

    


}
