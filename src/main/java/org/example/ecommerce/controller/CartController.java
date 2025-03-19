package org.example.ecommerce.controller;

import org.example.ecommerce.dtos.cart.CartCreateDto;
import org.example.ecommerce.dtos.cart.CartUpdateDto;
import org.example.ecommerce.payload.ApiResponse;
import org.example.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> addToCart(@ModelAttribute CartCreateDto cartCreateDto) {
        return ResponseEntity.ok(cartService.addToCart(cartCreateDto));
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updateCartItem(@ModelAttribute CartUpdateDto cartUpdateDto) {
        return ResponseEntity.ok(cartService.updateCartItem(cartUpdateDto));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<ApiResponse> removeItem(@PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeCartItem(productId));
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long userId) {
        ApiResponse response = cartService.clearCart(userId);
        return ResponseEntity.ok(response);
    }

}
