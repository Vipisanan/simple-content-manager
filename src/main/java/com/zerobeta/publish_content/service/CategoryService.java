package com.zerobeta.publish_content.service;

import com.zerobeta.publish_content.dto.CategoryCreateRequest;
import com.zerobeta.publish_content.dto.CategoryDto;
import com.zerobeta.publish_content.dto.CategoryUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryDto createCategory(CategoryCreateRequest request);

    Optional<CategoryDto> getCategoryById(Long id);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory(Long id, CategoryUpdateRequest request);

    void deleteCategory(Long id);
}
