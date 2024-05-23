package com.geeks.pixion.services;

import com.geeks.pixion.entities.Category;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.ApiResponse;
import com.geeks.pixion.payloads.CategoryAddDto;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryAddDto category);
    Category updateCategory(CategoryAddDto category, Long userId) throws ResourceNotFoundException;
    List<Category>getAllCategory();
    ApiResponse deleteCategory(Long id) throws ResourceNotFoundException;
    Category getCategoryById(Long categoryId) throws ResourceNotFoundException;

}
