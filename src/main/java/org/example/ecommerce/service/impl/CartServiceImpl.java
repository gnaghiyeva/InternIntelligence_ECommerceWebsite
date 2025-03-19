package org.example.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommerce.dtos.cart.CartCreateDto;
import org.example.ecommerce.dtos.cart.CartDto;
import org.example.ecommerce.dtos.cart.CartItemDto;
import org.example.ecommerce.dtos.cart.CartUpdateDto;
import org.example.ecommerce.model.Cart;
import org.example.ecommerce.model.CartItem;
import org.example.ecommerce.model.Product;
import org.example.ecommerce.model.User;
import org.example.ecommerce.payload.ApiResponse;
import org.example.ecommerce.repository.CartItemRepository;
import org.example.ecommerce.repository.CartRepository;
import org.example.ecommerce.repository.ProductRepository;
import org.example.ecommerce.repository.UserRepository;
import org.example.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse getCart(Long userId) {
        Optional<Cart> cartOptional = cartRepository.findById(userId);
        if (cartOptional.isEmpty()) {
            return new ApiResponse(false, "Cart not found");
        }
        Cart cart = cartOptional.get();
        List<CartItemDto> cartItems = cart.getCartItems().stream()
                .map(cartItem -> new CartItemDto(
                        cartItem.getProduct().getId(),
                        cartItem.getProduct().getName(),
                        cartItem.getProduct().getPrice(),
                        cartItem.getQuantity()
                )).collect(Collectors.toList());

        CartDto cartDto = new CartDto(cart.getId(), cartItems, cart.getTotalPrice());
        return new ApiResponse(true, "Cart fetched", cartDto);
    }

    @Override
    public ApiResponse addToCart(CartCreateDto cartCreateDto) {
        Optional<User> userOptional = userRepository.findById(cartCreateDto.getUserId());
        Optional<Product> productOptional = productRepository.findById(cartCreateDto.getProductId());
        if (userOptional.isEmpty() || productOptional.isEmpty()) {
            return new ApiResponse(false, "User or Product not found");
        }

        User user = userOptional.get();
        Product product = productOptional.get();

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setTotalPrice(0.0);
            return cartRepository.save(newCart);
        });

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartCreateDto.getQuantity());
        cartItemRepository.save(cartItem);

        cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * cartCreateDto.getQuantity()));

        cartRepository.save(cart);
        return new ApiResponse(true, "Product added to cart", getCart(user.getId()).getData());
    }

    @Override
    public ApiResponse updateCartItem(CartUpdateDto cartUpdateDto) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartUpdateDto.getCartItemId());
        if (cartItemOptional.isEmpty()) {
            return new ApiResponse(false, "Cart item not found");
        }
        CartItem cartItem = cartItemOptional.get();

        if(cartItem.getQuantity() != null ){
            cartItem.setQuantity(cartUpdateDto.getQuantity());
        }
        cartItemRepository.save(cartItem);
        return new ApiResponse(true, "Cart item updated");
    }

    @Override
    public ApiResponse removeCartItem(Long cartItemId) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()) {
            return new ApiResponse(false, "Cart item not found");
        }
        cartItemRepository.delete(cartItemOptional.get());
        return new ApiResponse(true, "Cart item removed");
    }

    @Override
    public ApiResponse clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for this user"));

        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        return new ApiResponse(true, "Cart has been cleared successfully");
    }
}
