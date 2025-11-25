package com.uade.tpo.demo.entity;

// OJO: si te marca error en jakarta.persistence,
// cambiá "jakarta" por "javax" en los imports.
import jakarta.persistence.*;

@Entity
@Table(name = "imagen")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Id del producto al que pertenece la imagen
    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    private String nombre;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    @Column(nullable = false)
    private byte[] datos;

    // ----- Getters y setters -----

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
