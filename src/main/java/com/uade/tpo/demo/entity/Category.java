package com.uade.tpo.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Category {

    public Category() {}
    public Category(String description) { this.description = description; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)   // opcional pero recomendable
    private String description;

    @JsonIgnore                                // evita bucles al serializar
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "categories")
    private List<Product> products = new ArrayList<>();
}
