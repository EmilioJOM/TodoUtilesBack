package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Cart;
import com.uade.tpo.demo.entity.Cupon;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.Venta;
import com.uade.tpo.demo.repository.CartRepository;
import com.uade.tpo.demo.repository.CuponRepository;
import com.uade.tpo.demo.repository.VentaRepository;

import jakarta.transaction.Transactional;

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

    @Autowired
    private CartRepository cartRepository;

    // Obtener todas las ventas
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    // Obtener ventas de un usuario
    public List<Venta> getVentasUsuario(Long idUsuario) {
        return ventaRepository.findByIdUsuario(idUsuario);
    }

    
    public Optional<Venta> getPendingVentaByUser(Long userId) {
        return ventaRepository.findByIdUsuario(userId)
                .stream()
                .filter(v -> "PENDING".equals(v.getEstado()))
                .findFirst();
    }

    @Transactional
    public Venta confirmarVenta(User user, String metodoPago, String codigoCupon) {

        Venta venta = getPendingVentaByUser(user.getId())
                .orElseThrow(() -> new RuntimeException("No hay venta pendiente para confirmar"));

        double total = venta.getTotal();

        // Aplicar cupón si existe
        if (codigoCupon != null && !codigoCupon.isEmpty()) {
            Optional<Cupon> cuponOpt = cuponRepository.findByCupon(codigoCupon);
            if (cuponOpt.isPresent()) {
                Cupon cupon = cuponOpt.get();
                if (cupon.getValidez().isAfter(LocalDateTime.now())) {
                    if ("porcentaje".equalsIgnoreCase(cupon.getTipo())) {
                        total -= total * cupon.getDescuento() / 100.0;
                    } else if ("monto".equalsIgnoreCase(cupon.getTipo())) {
                        total -= cupon.getDescuento();
                    }
                    venta.setIdCupon(cupon.getIdCupon());
                }
            }
        }

        venta.setMetodoPago(metodoPago);
        venta.setTotal(total);
        venta.setEstado("COMPRADA");
        venta.setFecha(LocalDateTime.now());
        ventaRepository.save(venta);

        // Actualizar carrito asociado
        Cart cart = venta.getCart();
        cart.setState("PURCHASED");
        cartRepository.save(cart);

        return venta;
    }

    @Transactional
    public Venta cancelarVenta(User user) {
        Venta venta = getPendingVentaByUser(user.getId())
                .orElseThrow(() -> new RuntimeException("No hay venta pendiente para cancelar"));

        venta.setEstado("CANCELADA");
        ventaRepository.save(venta);

        Cart cart = venta.getCart();
        cart.setState("CANCELLED");
        cartRepository.save(cart);

        return venta;
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


        venta.setTotal(total);
        return ventaRepository.save(venta);
    }
}