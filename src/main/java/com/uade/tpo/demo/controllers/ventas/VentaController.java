package com.uade.tpo.demo.controllers.ventas;

import com.uade.tpo.demo.entity.Venta;
import com.uade.tpo.demo.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // Obtener todas las ventas
    @GetMapping
    public List<Venta> getVentas() {
        return ventaService.getAllVentas();
    }

    // Obtener ventas de un usuario específico
    @GetMapping("/{idUsuario}")
    public List<Venta> getVentasUsuario(@PathVariable Long idUsuario) {
        System.out.println("GET: ventas/"+idUsuario.toString());
        return ventaService.getVentasUsuario(idUsuario);
    }

    // Crear nueva venta (ejemplo de uso en checkout)
    @PostMapping
    public Venta crearVenta(@RequestParam Long idUsuario,
                            @RequestParam Double total,
                            @RequestParam String metodoPago,
                            @RequestParam(required = false) Long idCupon) {
        System.out.println("POST: ventas");

        Venta venta = new Venta();
        venta.setIdUsuario(idUsuario);
        venta.setTotal(total);
        venta.setMetodoPago(metodoPago);
        venta.setIdCupon(idCupon);
        return ventaService.crearVenta(venta);
    }

    @PostMapping("/checkout")
    public Venta checkout(@RequestParam Long idUsuario,
                          @RequestParam Double total,
                          @RequestParam String metodoPago,
                          @RequestParam(required = false) String codigoCupon) {
        Venta venta = new Venta();
        venta.setIdUsuario(idUsuario);
        venta.setTotal(total);
        venta.setMetodoPago(metodoPago);
        return ventaService.checkout(venta, codigoCupon);
    }
}
