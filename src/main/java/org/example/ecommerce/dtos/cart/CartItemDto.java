package org.example.ecommerce.dtos.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private Double price;
    private Long quantity;

//    public CartItemDto(Long id, String name, double price, Long quantity) {
//    }
}
