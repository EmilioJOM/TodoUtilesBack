package com.uade.tpo.demo.controllers.ventas;

import com.uade.tpo.demo.entity.Cupon;
import com.uade.tpo.demo.service.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cupones")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    // Obtener todos los cupones
    @GetMapping
    public List<Cupon> getCupones() {
        return cuponService.getAllCupones();
    }

    // Buscar cupón por código
    @GetMapping("/{codigo}")
    public Optional<Cupon> getCuponByCodigo(@PathVariable String codigo) {
        return cuponService.getCuponByCodigo(codigo);
    }

    // Crear nuevo cupón
    @PostMapping
    public Cupon crearCupon(@RequestBody Cupon cupon) {
        return cuponService.crearCupon(cupon);
    }

    // Eliminar cupón
    @DeleteMapping("/{idCupon}")
    public void eliminarCupon(@PathVariable Long idCupon) {
        cuponService.eliminarCupon(idCupon);
    }
}
