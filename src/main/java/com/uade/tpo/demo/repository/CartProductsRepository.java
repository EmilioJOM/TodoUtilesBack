package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.CartProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface CartProductsRepository extends JpaRepository<CartProducts, Long> {

    @Query("SELECT cp FROM CartProducts cp WHERE cp.cart.id = :cartId AND cp.product.id = :productId")
    Optional<CartProducts> findByCartIdAndProductId(@Param("cartId") Long cartId,
                                                    @Param("productId") Long productId);
}

