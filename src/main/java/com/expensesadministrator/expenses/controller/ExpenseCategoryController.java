package com.expensesadministrator.expenses.controller;

import com.expensesadministrator.expenses.dto.request.ExpenseCategoryRequest;
import com.expensesadministrator.expenses.dto.response.ExpenseCategoryResponseDto;
import com.expensesadministrator.expenses.entity.ExpenseCategory;
import com.expensesadministrator.expenses.service.ExpenseCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    @PostMapping
    public ResponseEntity<ExpenseCategoryResponseDto> createCategory(@RequestBody ExpenseCategoryRequest expenseCategoryRequest) {
            Optional<ExpenseCategory> existingCategoryOpt = expenseCategoryService.getCategoryByName(expenseCategoryRequest.name());
            ExpenseCategoryResponseDto categoryDto = expenseCategoryService.save(existingCategoryOpt.get());
            return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseCategoryResponseDto>> getAllCategories() {
        List<ExpenseCategoryResponseDto> categories = expenseCategoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ExpenseCategoryResponseDto> getCategoryByName(@PathVariable String name) {
        Optional<ExpenseCategoryResponseDto> category = expenseCategoryService.getCategoryByNameDto(name);
        return category.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping
    public ResponseEntity<ExpenseCategoryResponseDto> updateCategory(@RequestBody ExpenseCategoryRequest expenseCategoryRequest) {
        Optional<ExpenseCategory> existingCategoryOpt = expenseCategoryService.getCategoryByName(expenseCategoryRequest.name());

        if (existingCategoryOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ExpenseCategory existingCategory = existingCategoryOpt.get();
        existingCategory.setName(expenseCategoryRequest.name());

        Optional<ExpenseCategoryResponseDto> updatedCategory = expenseCategoryService.updateCategory(existingCategory);

        return updatedCategory
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        expenseCategoryService.deleteCategory(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}