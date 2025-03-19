package org.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cartitem")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Product product;

    private Long quantity;

}
