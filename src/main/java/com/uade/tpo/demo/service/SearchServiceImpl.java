package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final ProductRepository productRepository;

    public SearchServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductsByPrice(double price) {
        return productRepository.findByPriceLessThanEqual(price);
    }

    @Override
    public List<Product> getProductsByDescription(String q) {
        if (q == null || q.isBlank()) return Collections.emptyList();
        return productRepository.findByDescriptionContainingIgnoreCase(q);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        if (category == null || category.isBlank()) return Collections.emptyList();
        try {
            Long id = Long.valueOf(category);
            return productRepository.findByCategories_Id(id);
        } catch (NumberFormatException e) {
            // si no es número, devolvemos vacío
            return Collections.emptyList();
        }
    }
}
