package com.uade.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Entity
@Table(name = "images")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Imagen {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private Blob image;   // <-- antes decía filename (Blob). Cambiar a image
}
