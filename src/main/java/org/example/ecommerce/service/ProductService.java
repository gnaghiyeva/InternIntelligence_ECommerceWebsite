package org.example.ecommerce.service;

import org.example.ecommerce.dtos.product.ProductCreateDto;
import org.example.ecommerce.dtos.product.ProductDto;
import org.example.ecommerce.dtos.product.ProductUpdateDto;
import org.example.ecommerce.payload.ApiResponse;

import java.util.List;

public interface ProductService {
    ApiResponse addProduct(ProductCreateDto productCreateDto);
    List<ProductDto> getAllProducts();
    ApiResponse getProductById(Long id);
    List<ProductDto> getProductsByCategory(Long categoryId);
    ApiResponse updateProduct(Long id, ProductUpdateDto productUpdateDto);
    ApiResponse deleteProduct(Long id);
}
