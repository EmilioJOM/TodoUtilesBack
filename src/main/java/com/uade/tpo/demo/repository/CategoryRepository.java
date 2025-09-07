package com.uade.tpo.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.description = :categoryDescription")
    List<Product> findByDescription(@Param("categoryDescription") String categoryDescription);
}
