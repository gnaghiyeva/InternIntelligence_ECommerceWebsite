package org.example.ecommerce.controller;

import org.example.ecommerce.dtos.category.CategoryDto;
import org.example.ecommerce.dtos.product.ProductCreateDto;
import org.example.ecommerce.dtos.product.ProductDto;
import org.example.ecommerce.dtos.product.ProductUpdateDto;
import org.example.ecommerce.payload.ApiResponse;
import org.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse>createProduct(@ModelAttribute ProductCreateDto productCreateDto) {
        ApiResponse response = productService.addProduct(productCreateDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> response = productService.getAllProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping(value = "/id")
    public ResponseEntity<ApiResponse> getProductById(@RequestParam Long id) {
        ApiResponse productDetail = productService.getProductById(id);
        return ResponseEntity.ok(productDetail);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updateProduct(@RequestParam Long id, @ModelAttribute ProductUpdateDto productUpdateDto) {
       ApiResponse response = productService.updateProduct(id, productUpdateDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping(value = "/id")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestParam Long id) {
        ApiResponse response = productService.deleteProduct(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
