package com.uade.tpo.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cupones")
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCupon;

    private String cupon;       // Código del cupón
    private Integer descuento;  // % o monto fijo
    private String tipo;        // "porcentaje" o "monto"
    private LocalDateTime validez; // Fecha de expiración

    // --- Getters y Setters ---
    public Long getIdCupon() {
        return idCupon;
    }

    public void setIdCupon(Long idCupon) {
        this.idCupon = idCupon;
    }

    public String getCupon() {
        return cupon;
    }

    public void setCupon(String cupon) {
        this.cupon = cupon;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getValidez() {
        return validez;
    }

    public void setValidez(LocalDateTime validez) {
        this.validez = validez;
    }
}
