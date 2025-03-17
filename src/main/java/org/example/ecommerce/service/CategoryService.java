package org.example.ecommerce.service;

import org.example.ecommerce.dtos.category.CategoryCreateDto;
import org.example.ecommerce.dtos.category.CategoryDto;
import org.example.ecommerce.dtos.category.CategoryUpdateDto;
import org.example.ecommerce.payload.ApiResponse;

import java.util.List;

public interface CategoryService {
    ApiResponse addCategory(CategoryCreateDto categoryCreateDto);
    List<CategoryDto> getAllCategories();
    ApiResponse getCategoryById(Long id);
    ApiResponse updateCategory(Long id, CategoryUpdateDto categoryUpdateDto);
    ApiResponse deleteCategory(Long id);
}
