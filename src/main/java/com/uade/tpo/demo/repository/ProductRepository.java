package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

<<<<<<< HEAD
    // Precio <= X
    List<Product> findByPriceLessThanEqual(double price);

    // Descripción contiene (ignora mayúsculas/minúsculas)
    List<Product> findByDescriptionContainingIgnoreCase(String description);
=======
    //Filtra por descripcion
    @Query("SELECT p FROM Product p WHERE p.description = :description")
    List<Product> findByDescription(@Param("description") String description);

    //filtra por precio
    @Query("SELECT p FROM Product p WHERE p.price <= :price")
    List<Product> findByPrice(@Param("price") Double price);

    //filtra por categoria
    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> findByCategory(@Param("category") String category);

    // filtra por categoria y precio
    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.price <= :price")
    List<Product> findByCategoryPrice(@Param("category") String category ,@Param("price") Double price);
>>>>>>> origin/main

    // Por ID de categoría (relación ManyToMany Product.categories)
    List<Product> findByCategories_Id(Long categoryId);
}
