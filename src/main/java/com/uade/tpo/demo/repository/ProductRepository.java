package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //Filtra por descripcion
    @Query("SELECT p FROM Product p WHERE p.description = :description")
    List<Product> findByDescription(@Param("description") String description);

    //filtra por precio
    @Query("SELECT p FROM Product p WHERE p.price <= :price")
    List<Product> findByPrice(@Param("price") Double price);

    //filtra por categoria
    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.description = :description")
    List<Product> findByCategoryDescription(@Param("description") String description);

    // filtra por categoria y precio
    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.description = :description AND p.price <= :price")
    List<Product> findByCategoryDescriptionAndPrice(@Param("description") String description, @Param("price") Double price);

    // Por ID de categoría (relación ManyToMany Product.categories)
    List<Product> findByCategories_Id(Long categoryId);
}

