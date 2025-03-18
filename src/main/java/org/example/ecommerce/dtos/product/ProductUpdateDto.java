package org.example.ecommerce.dtos.product;

import lombok.Data;

@Data
public class ProductUpdateDto {
    private String name;
    private String description;
    private Double price;
    private Long categoryId;
}
