package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Cupon;
import com.uade.tpo.demo.repository.CuponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    public List<Cupon> getAllCupones() {
        return cuponRepository.findAll();
    }

    public Optional<Cupon> getCuponByCodigo(String codigo) {
        return cuponRepository.findByCupon(codigo);
    }

    public Cupon crearCupon(Cupon cupon) {
        return cuponRepository.save(cupon);
    }

    public void eliminarCupon(Long idCupon) {
        cuponRepository.deleteById(idCupon);
    }

    public boolean esValido(Cupon cupon) {
        return cupon.getValidez().isAfter(LocalDateTime.now());
    }
}
