package org.example.ecommerce.dtos.product;

import lombok.Data;
import org.example.ecommerce.dtos.category.CategoryDto;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private CategoryDto category;
}
