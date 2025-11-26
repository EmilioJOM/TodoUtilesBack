package com.uade.tpo.demo.entity;

import jakarta.persistence.*;

/**
 * Entidad que representa la imagen binaria de un producto en la base de datos.
 */
@Entity
@Table(name = "imagen")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Columna para asociar la imagen con el producto correspondiente.
    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    // Nombre original del archivo (ej: foto.png).
    private String nombre;

    // Tipo de contenido (ej: image/png).
    @Column(name = "content_type")
    private String contentType;

    // Campo donde se almacenan los bytes binarios de la imagen.
    // Usamos LONGBLOB para forzar a Hibernate a usar el tipo de datos más grande,
    // corrigiendo el error de truncamiento de datos.
    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] datos;

    // Constructor vacío (necesario para JPA/Hibernate)
    public Imagen() {
    }

    // --- Getters y setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getDatos() {
        return datos;
    }

    public void setDatos(byte[] datos) {
        this.datos = datos;
    }
}