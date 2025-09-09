package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Long> {
    Optional<Cupon> findByCupon(String cupon); // Buscar por código de cupón
}
