package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Venta;
import com.uade.tpo.demo.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public List<Venta> getVentasUsuario(Long idUsuario) {
        return ventaRepository.findByIdUsuario(idUsuario);
    }

    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    public Venta crearVenta(Long idUsuario, Double total, String metodoPago, Long idCupon) {
        Venta venta = new Venta();
        venta.setIdUsuario(idUsuario);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(total);
        venta.setMetodoPago(metodoPago);
        venta.setIdCupon(idCupon);

        return ventaRepository.save(venta);
    }
}
