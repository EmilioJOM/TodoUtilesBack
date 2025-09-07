package com.uade.tpo.demo.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.uade.tpo.demo.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //Filtra por descripcion
    @Query("SELECT p FROM Product p WHERE p.description = :description")
    List<Product> findByDescription(@Param("description") String description);

    //filtra por precio
    @Query("SELECT p FROM Product p WHERE p.price <= :price")
    List<Product> findByPrice(@Param("price") Double price);

    //filtra por Categoria
    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> findByCategory(@Param("category") String category);

}