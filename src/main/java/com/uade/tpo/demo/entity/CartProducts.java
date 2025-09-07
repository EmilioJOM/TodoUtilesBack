package com.uade.tpo.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cart_products")
public class CartProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public CartProducts() {}
}
