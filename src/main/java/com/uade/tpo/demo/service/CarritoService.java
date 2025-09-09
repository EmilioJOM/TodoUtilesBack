package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Carrito;
import com.uade.tpo.demo.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    public List<Carrito> getCarritoUsuario(Long idUsuario) {
        return carritoRepository.findByIdUsuario(idUsuario);
    }

    public Carrito agregarProducto(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public void eliminarProducto(Long idCarrito) {
        carritoRepository.deleteById(idCarrito);
    }

    public void vaciarCarrito(Long idUsuario) {
        List<Carrito> items = carritoRepository.findByIdUsuario(idUsuario);
        carritoRepository.deleteAll(items);
    }
}
