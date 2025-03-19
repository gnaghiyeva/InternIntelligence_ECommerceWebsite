package org.example.ecommerce.service;

import org.example.ecommerce.dtos.cart.CartCreateDto;
import org.example.ecommerce.dtos.cart.CartUpdateDto;
import org.example.ecommerce.payload.ApiResponse;

public interface CartService {
    ApiResponse getCart(Long userId);
    ApiResponse addToCart(CartCreateDto cartCreateDto);
    ApiResponse updateCartItem(CartUpdateDto cartUpdateDto);
    ApiResponse removeCartItem(Long cartItemId);
    ApiResponse clearCart(Long userId);
}
