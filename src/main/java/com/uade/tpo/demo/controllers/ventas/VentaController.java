package com.uade.tpo.demo.controllers.ventas;

import com.uade.tpo.demo.entity.Cart;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.Venta;
import com.uade.tpo.demo.entity.dto.CartProductResponseDTO;
import com.uade.tpo.demo.entity.dto.MessageResponseDTO;
import com.uade.tpo.demo.entity.dto.VentaDetailDTO;
import com.uade.tpo.demo.entity.dto.VentaResponseDTO;
import com.uade.tpo.demo.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<VentaResponseDTO> getVentas() {
        return ventaService.getAllVentas()
                .stream()
                .map(v -> {
                    String nombreUsuario = "";
                    if (v.getCart() != null && v.getCart().getUser() != null) {
                        nombreUsuario = v.getCart().getUser().getFirstName() + " " + v.getCart().getUser().getLastName();
                    }
                    return new VentaResponseDTO(
                            v.getIdVenta(),
                            nombreUsuario,
                            v.getFecha(),
                            v.getTotal(),
                            v.getMetodoPago(),
                            v.getEstado()
                    );
                })
                .collect(Collectors.toList());
    }

        // Obtener ventas de un usuario específico
    @GetMapping("/my")
    public ResponseEntity<?> getMisCompras(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario no autenticado");
        }

        List<VentaResponseDTO> ventas = ventaService.getVentasUsuario(user.getId())
                .stream()
                .map(v -> new VentaResponseDTO(
                        v.getIdVenta(),
                        user.getFirstName() + " " + user.getLastName(),
                        v.getFecha(),
                        v.getTotal(),
                        v.getMetodoPago(),
                        v.getEstado()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ventas);
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
    

    @GetMapping("/{idVenta}/details")
    public ResponseEntity<?> getVentaDetails(
        @PathVariable Long idVenta,
        @AuthenticationPrincipal User user
    ) {
        Venta venta = ventaService.getVentaById(idVenta);

        if (venta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Venta no encontrada");
        }

        if (!user.getRole().name().equals("ADMIN") && !venta.getIdUsuario().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No puedes ver compras de otro usuario");
        }

        Cart cart = venta.getCart();

        var productos = cart.getCartProducts().stream()
                .map(cp -> new CartProductResponseDTO(
                        cp.getProduct().getId(),
                        cp.getProduct().getDescription(),
                        cp.getProduct().getPrice(),
                        cp.getQuantity(),
                        cp.getProduct().getStock()
                )).toList();

        return ResponseEntity.ok(
            new VentaDetailDTO(
                venta.getIdVenta(),
                venta.getFecha(),
                venta.getTotal(),
                venta.getMetodoPago(),
                venta.getEstado(),
                productos
            )
        );
    }
}