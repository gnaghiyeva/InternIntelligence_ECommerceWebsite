package org.example.ecommerce.service.impl;

import org.example.ecommerce.dtos.cart.CartDto;
import org.example.ecommerce.dtos.cart.CartItemDto;
import org.example.ecommerce.dtos.order.OrderDto;
import org.example.ecommerce.dtos.order.OrderItemDto;
import org.example.ecommerce.model.*;
import org.example.ecommerce.payload.ApiResponse;
import org.example.ecommerce.repository.OrderRepository;
import org.example.ecommerce.repository.ProductRepository;
import org.example.ecommerce.repository.UserRepository;
import org.example.ecommerce.service.CartService;
import org.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @Override
    public ApiResponse createOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ApiResponse response = cartService.getCart(userId);

        if (!response.isSuccess() || response.getData() == null) {
            return new ApiResponse(false, "Cart not found or empty");
        }

        CartDto cartDto = (CartDto) response.getData();

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0.0;

        for (CartItemDto cartItemDto : cartDto.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(productRepository.findById(cartItemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found")));
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setPrice(cartItemDto.getPrice() * cartItemDto.getQuantity());
            orderItems.add(orderItem);
            totalPrice += orderItem.getPrice();
        }

        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

        orderRepository.save(order);
        cartService.clearCart(userId);

        return new ApiResponse(true, "Order created successfully", order);
    }


    @Override
    public ApiResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
        return new ApiResponse(true, "Order updated successfully");
    }

    @Override
    public List<OrderDto> getOrdersByUser(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(order -> {

            List<OrderItemDto> items = order.getOrderItems().stream()
                    .map(item -> new OrderItemDto(item.getProduct().getId(),
                            item.getProduct().getName(),
                            item.getQuantity(),
                            item.getPrice()))
                    .collect(Collectors.toList());

            return new OrderDto(order.getId(), items, order.getTotalPrice(), order.getStatus());
        }).collect(Collectors.toList());
    }

    @Override
    public ApiResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED) {
            return new ApiResponse(false, "Order cannot be canceled");
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return new ApiResponse(true, "Order canceled successfully");
    }
}
