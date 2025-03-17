package org.example.ecommerce.controller;

import org.example.ecommerce.dtos.category.CategoryCreateDto;
import org.example.ecommerce.dtos.category.CategoryDto;
import org.example.ecommerce.dtos.category.CategoryUpdateDto;
import org.example.ecommerce.payload.ApiResponse;
import org.example.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createCategory(@ModelAttribute CategoryCreateDto categoryCreateDto) {
        ApiResponse response = categoryService.addCategory(categoryCreateDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<CategoryDto> response = categoryService.getAllCategories();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/id")
    public ResponseEntity<ApiResponse> getCategoryById(@RequestParam Long id) {
        ApiResponse categoryDetail = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryDetail);
    }

    @PutMapping(value = "/id", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updateCategory(@RequestParam Long id, @ModelAttribute CategoryUpdateDto categoryUpdateDto) {
        ApiResponse response = categoryService.updateCategory(id, categoryUpdateDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/id")
    public ResponseEntity<ApiResponse> deleteCategory(@RequestParam Long id) {
        ApiResponse response = categoryService.deleteCategory(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
