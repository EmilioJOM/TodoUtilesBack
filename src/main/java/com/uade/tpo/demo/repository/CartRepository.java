package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;



import java.util.Optional;

@Repository


public interface CartRepository extends JpaRepository<Cart, Long> {
    
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.state = 'ACTIVE'")
    Optional<Cart> findActiveCart(@Param("userId") Long userId);

    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.cartProducts WHERE c.user.id = :userId AND c.state = 'ACTIVE'")
    Optional<Cart> findByUserIdAndStateTrueWithProducts(@Param("userId") Long userId);

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.state = 'PENDING'")
    Optional<Cart> findPendingCartByUser(@Param("userId") Long userId);

}

