package org.example.ecommerce.dtos.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateDto {
    private Long cartItemId;
    private Long quantity;
}
