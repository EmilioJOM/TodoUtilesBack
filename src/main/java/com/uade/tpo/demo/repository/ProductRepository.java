package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Precio <= X
    List<Product> findByPriceLessThanEqual(double price);

    // Descripción contiene (case-insensitive)
    List<Product> findByDescriptionContainingIgnoreCase(String description);

    // Por ID de categoría (relación ManyToMany Product.categories)
    List<Product> findByCategories_Id(Long categoryId);
}
