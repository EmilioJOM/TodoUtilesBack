package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Product;
import java.util.List;

public interface SearchService {

    // Coinciden con lo que espera SearchController
    List<Product> getProductsByPrice(double price);

    List<Product> getProductsByDescription(String q);

    // En el controller viene como String: lo convertimos a ID
    List<Product> getProductsByCategory(String category);
}
