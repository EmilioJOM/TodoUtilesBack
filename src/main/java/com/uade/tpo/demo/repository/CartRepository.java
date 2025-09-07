package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Cart;
import com.uade.tpo.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.user = :user AND c.state = true")
    Optional<Cart> findByUserAndStateTrue(@Param("user") User user);
}

