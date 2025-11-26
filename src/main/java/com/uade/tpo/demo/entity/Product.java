package com.uade.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Product {

    public Product(){}

    public Product(String description, int stock, double price){
        this.description=description;
        this.stock=stock;
        this.price=price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description; //la descripcion actua como nombre

    @Column 
    private String extraInfo; //esta actua como descripcion

    @Column
    private int stock;

    @Column
    private double price;


    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imagen_id")
    private Imagen imagen;


    public void addCategory(Category category){
        this.categories.add(category);
    }

    public void deleteCategory(Category category){
        this.categories.remove(category);
    }


}
