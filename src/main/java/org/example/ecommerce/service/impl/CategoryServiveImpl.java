package org.example.ecommerce.service.impl;

import org.example.ecommerce.dtos.category.CategoryCreateDto;
import org.example.ecommerce.dtos.category.CategoryDto;
import org.example.ecommerce.dtos.category.CategoryUpdateDto;
import org.example.ecommerce.model.Category;
import org.example.ecommerce.payload.ApiResponse;
import org.example.ecommerce.repository.CategoryRepository;
import org.example.ecommerce.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiveImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponse addCategory(CategoryCreateDto categoryCreateDto) {
        Category category = modelMapper.map(categoryCreateDto, Category.class);
        categoryRepository.save(category);
        return new ApiResponse(true, "Category created");
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public ApiResponse getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            return new ApiResponse(false, "Category not found");
        }
        CategoryDto categoryDto = modelMapper.map(optionalCategory.get(), CategoryDto.class);
        return new ApiResponse(true, "Category found", categoryDto);
    }

    @Override
    public ApiResponse updateCategory(Long id, CategoryUpdateDto categoryUpdateDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            return new ApiResponse(false, "Category not found");
        }
        Category category = optionalCategory.get();
        if(categoryUpdateDto.getName() != null && !categoryUpdateDto.getName().isEmpty() && categoryUpdateDto.getName().isBlank()){
            category.setName(categoryUpdateDto.getName());
        }
        categoryRepository.save(category);
        return new ApiResponse(true, "Category updated");
    }

    @Override
    public ApiResponse deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            return new ApiResponse(false, "Category not found");
        }
        categoryRepository.delete(optionalCategory.get());
        return new ApiResponse(true, "Category deleted");
    }
}
