package org.example.ecommerce.service;

import org.example.ecommerce.dtos.order.OrderDto;
import org.example.ecommerce.model.Order;
import org.example.ecommerce.model.OrderStatus;
import org.example.ecommerce.payload.ApiResponse;

import java.util.List;

public interface OrderService {
    ApiResponse createOrder(Long userId);
    ApiResponse updateOrderStatus(Long orderId, OrderStatus status);
    List<OrderDto> getOrdersByUser(Long userId);
    ApiResponse cancelOrder(Long orderId);
}
