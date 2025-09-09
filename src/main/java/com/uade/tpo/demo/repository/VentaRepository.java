package com.uade.tpo.demo.repository;

import com.uade.tpo.demo.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByIdUsuario(Long idUsuario);
}
