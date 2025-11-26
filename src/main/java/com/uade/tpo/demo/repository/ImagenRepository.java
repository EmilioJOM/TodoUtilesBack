package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImagenRepository extends JpaRepository<Imagen, Long> {

    Optional<Imagen> findByProductoId(Long productoId);
}
