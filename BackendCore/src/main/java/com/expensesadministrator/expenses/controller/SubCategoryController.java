package com.expensesadministrator.expenses.controller;

import com.expensesadministrator.expenses.dto.request.SubCategoryRequestDto;
import com.expensesadministrator.expenses.entity.SubCategory;
import com.expensesadministrator.expenses.service.SubCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    // Create a new SubCategory
    @PostMapping
    public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategoryRequestDto subCategoryRequestDto){
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryRequestDto.getName());
        subCategory.setParent(subCategoryRequestDto.getParent());  // Set parent if exists
        SubCategory createdSubCategory = subCategoryService.createSubCategory(subCategory);
        return new ResponseEntity<>(createdSubCategory, HttpStatus.CREATED);
    }

    // Get all subcategories
    @GetMapping
    public ResponseEntity<List<SubCategory>> getAllSubCategories() {
        List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
        return new ResponseEntity<>(subCategories, HttpStatus.OK);
    }

    // Get subcategory by ID
    @GetMapping("/{id}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable String id) {
        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(id);
        return subCategory.map(
                        response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update a subcategory
    @PutMapping("/{id}")
    public ResponseEntity<SubCategory> updateSubCategory(@PathVariable String id, @RequestBody SubCategory subCategoryDetails) {
        try {
            SubCategory updatedSubCategory = subCategoryService.updateSubCategory(id, subCategoryDetails);
            return new ResponseEntity<>(updatedSubCategory, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a subcategory
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable String id) {
        try {
            subCategoryService.deleteSubCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get subcategories by parent category ID
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<SubCategory>> getSubCategoriesByParentId(@PathVariable String parentId) {
        List<SubCategory> subCategories = subCategoryService.getSubCategoriesByParentId(parentId);
        if (subCategories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(subCategories, HttpStatus.OK);
    }
}

