package com.uade.tpo.demo.controllers.ventas;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.Venta;
import com.uade.tpo.demo.entity.dto.MessageResponseDTO;
import com.uade.tpo.demo.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // Confirmar venta pendiente del usuario logueado
    @PutMapping("/confirm")
    public ResponseEntity<?> confirmarVenta(
            @AuthenticationPrincipal User user,
            @RequestParam String metodoPago,
            @RequestParam(required = false) String codigoCupon) {

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario no autenticado");
        }

        try {
            ventaService.confirmarVenta(user, metodoPago, codigoCupon);
            return ResponseEntity.ok(new MessageResponseDTO("Venta confirmada correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    // Cancelar venta pendiente del usuario logueado
    @PutMapping("/cancel")
    public ResponseEntity<?> cancelarVenta(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario no autenticado");
        }

        try {
            ventaService.cancelarVenta(user);
            return ResponseEntity.ok(new MessageResponseDTO("Venta cancelada correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }



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
