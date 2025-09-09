package com.uade.tpo.demo.controllers.ventas;

import com.uade.tpo.demo.entity.Carrito;
import com.uade.tpo.demo.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // Obtener carrito de un usuario
    @GetMapping("/{idUsuario}")
    public List<Carrito> getCarritoUsuario(@PathVariable Long idUsuario) {
        return carritoService.getCarritoUsuario(idUsuario);
    }

    // Agregar producto al carrito
    @PostMapping
    public Carrito agregarProducto(@RequestBody Carrito carrito) {
        return carritoService.agregarProducto(carrito);
    }

    // Eliminar un producto del carrito
    @DeleteMapping("/{idCarrito}")
    public void eliminarProducto(@PathVariable Long idCarrito) {
        carritoService.eliminarProducto(idCarrito);
    }

    // Vaciar carrito de un usuario
    @DeleteMapping("/usuario/{idUsuario}")
    public void vaciarCarrito(@PathVariable Long idUsuario) {
        carritoService.vaciarCarrito(idUsuario);
    }
}
