package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Cupon;
import com.uade.tpo.demo.entity.Venta;
import com.uade.tpo.demo.repository.CuponRepository;
import com.uade.tpo.demo.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private CuponRepository cuponRepository;

    // Obtener todas las ventas
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    // Obtener ventas de un usuario
    public List<Venta> getVentasUsuario(Long idUsuario) {
        return ventaRepository.findByIdUsuario(idUsuario);
    }

    // Crear venta normal (sin cupón)
    public Venta crearVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    // Checkout con cupón
    public Venta checkout(Venta venta, String codigoCupon) {
        double total = venta.getTotal();

        //  Aplicación del cupón
        if (codigoCupon != null && !codigoCupon.isEmpty()) {
            Optional<Cupon> cuponOpt = cuponRepository.findByCupon(codigoCupon);

            if (cuponOpt.isPresent()) {
                Cupon cupon = cuponOpt.get();

                // Validar fecha de vencimiento
                if (cupon.getValidez().isAfter(LocalDateTime.now())) {
                    if ("porcentaje".equalsIgnoreCase(cupon.getTipo())) {
                        total = total - (total * cupon.getDescuento() / 100.0);
                    } else if ("monto".equalsIgnoreCase(cupon.getTipo())) {
                        total = total - cupon.getDescuento();
                    }
                }
            }
        }

        // Setear el total final y guardar venta
        venta.setTotal(total);
        return ventaRepository.save(venta);
    }
}