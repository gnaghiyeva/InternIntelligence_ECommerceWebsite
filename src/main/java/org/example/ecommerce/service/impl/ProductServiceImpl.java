package org.example.ecommerce.service.impl;

import org.example.ecommerce.dtos.category.CategoryDto;
import org.example.ecommerce.dtos.product.ProductCreateDto;
import org.example.ecommerce.dtos.product.ProductDto;
import org.example.ecommerce.dtos.product.ProductUpdateDto;
import org.example.ecommerce.model.Category;
import org.example.ecommerce.model.Product;
import org.example.ecommerce.payload.ApiResponse;
import org.example.ecommerce.repository.CategoryRepository;
import org.example.ecommerce.repository.ProductRepository;
import org.example.ecommerce.service.CategoryService;
import org.example.ecommerce.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponse addProduct(ProductCreateDto productCreateDto) {

        Category category = categoryRepository.findById(productCreateDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = new Product();
        product.setName(productCreateDto.getName());
        product.setDescription(productCreateDto.getDescription());
        product.setPrice(productCreateDto.getPrice());
        product.setCategory(category);

        productRepository.save(product);
        return new ApiResponse(true, "Product added successfully", product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        return productDtos;
    }

    @Override
    public ApiResponse getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            return new ApiResponse(false, "Product not found");
        }
        ProductDto productDto = modelMapper.map(optionalProduct.get(), ProductDto.class);
        return new ApiResponse(true, "Product found", productDto);
    }

    @Override
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public ApiResponse updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isEmpty()) {
            return new ApiResponse(false,"Product not found");
        }
        Product existingProduct = existingProductOptional.get();

        if(productUpdateDto.getName() != null && !productUpdateDto.getName().isEmpty() && !productUpdateDto.getName().isBlank()){
            existingProduct.setName(productUpdateDto.getName());
        }
        if(productUpdateDto.getDescription() != null && !productUpdateDto.getDescription().isEmpty() && !productUpdateDto.getDescription().isBlank()){
            existingProduct.setDescription(productUpdateDto.getDescription());
        }
        if (productUpdateDto.getPrice() != null) {
            existingProduct.setPrice(productUpdateDto.getPrice());
        }
        if (productUpdateDto.getCategoryId() != null) {
            Optional<Category> categoryOptional = categoryRepository.findById(productUpdateDto.getCategoryId());
            if (categoryOptional.isPresent()) {
                existingProduct.setCategory(categoryOptional.get());
            } else {
                return new ApiResponse(false, "Category not found");
            }
        }
        productRepository.save(existingProduct);
        return new ApiResponse(true,"Product updated successfully", existingProduct);
    }

    @Override
    public ApiResponse deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            return new ApiResponse(false, "Product not found");
        }
        productRepository.delete(optionalProduct.get());
        return new ApiResponse(true, "Product deleted");
    }

}
