package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    List<Carrito> findByIdUsuario(Long idUsuario);
}
