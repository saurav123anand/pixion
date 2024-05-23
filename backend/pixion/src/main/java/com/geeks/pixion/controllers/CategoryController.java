package com.geeks.pixion.controllers;

import com.geeks.pixion.entities.Category;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.ApiResponse;
import com.geeks.pixion.payloads.CategoryAddDto;
import com.geeks.pixion.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<Category>crateCategory(@Valid @RequestBody CategoryAddDto categoryAddDto){
        return new ResponseEntity<>(categoryService.createCategory(categoryAddDto), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public Category updateCategory(@Valid @RequestBody CategoryAddDto requestBody, @RequestParam Long categoryId)throws ResourceNotFoundException {
        return categoryService.updateCategory(requestBody, categoryId);
    }

    @GetMapping("/all")
    public List<Category> findAllCategory() throws ResourceNotFoundException {
        return categoryService.getAllCategory();
    }

    @GetMapping("/byId/{id}")
    public Category findCategoryById(@PathVariable Long id) throws ResourceNotFoundException {
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/byId/{id}")
    public ApiResponse deleteCategory(@PathVariable long id)throws ResourceNotFoundException{
        return categoryService.deleteCategory(id);
    }

}