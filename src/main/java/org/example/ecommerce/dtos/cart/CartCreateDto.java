package org.example.ecommerce.dtos.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartCreateDto {
    private Long userId;
    private Long productId;
    private Long quantity;
}
