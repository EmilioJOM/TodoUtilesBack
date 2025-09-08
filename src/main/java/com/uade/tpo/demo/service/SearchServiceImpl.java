package com.uade.tpo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.repository.ProductRepository;


@Service
public class SearchServiceImpl implements SearchService {
    
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProductsByPrice(double price){
        return productRepository.findByPrice(price);
    }

    public List<Product> getProductsByDescription(String description){
        return productRepository.findByDescription(description);
    }

    public List<Product> getProductsByCategory(String category){
        return productRepository.findByCategory(category);

    }
    
}
