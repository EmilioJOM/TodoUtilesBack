package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

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

    // Por ID de categoría (relación ManyToMany Product.categories)
    List<Product> findByCategories_Id(Long categoryId);
}
