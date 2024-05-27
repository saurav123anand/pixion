package com.geeks.pixion.services.impl;

import com.geeks.pixion.constants.Constants;
import com.geeks.pixion.entities.Category;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.ApiResponse;
import com.geeks.pixion.payloads.CategoryAddDto;
import com.geeks.pixion.repositiories.CategoryRepository;
import com.geeks.pixion.services.CategoryService;
import com.geeks.pixion.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Utils utils;
    @Override
    public Category createCategory(CategoryAddDto request) {
        Category category = modelMapper.map(request, Category.class);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(CategoryAddDto request, Long categoryId) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException(Constants.CATEGORY_EXCEPTION_MSG +categoryId));
        Category updatedCategory = utils.validateAndSetFieldValue(request, category);
        return categoryRepository.save(updatedCategory);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long categoryId) throws ResourceNotFoundException {
        return categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException(Constants.CATEGORY_EXCEPTION_MSG +categoryId));
    }

    @Override
    public ApiResponse deleteCategory(Long categoryId) throws ResourceNotFoundException {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException(Constants.CATEGORY_EXCEPTION_MSG +categoryId));
        categoryRepository.delete(category);
        return new ApiResponse("Category successfully deleted for user id"+categoryId,true, HttpStatus.OK.value());
    }

}
