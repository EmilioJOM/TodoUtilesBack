package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Product;
import java.util.List;

public interface SearchService {
    List<Product> getProductsByPrice(double price);
    List<Product> getProductsByDescription(String q);
    List<Product> getProductsByCategory(String category);
}
