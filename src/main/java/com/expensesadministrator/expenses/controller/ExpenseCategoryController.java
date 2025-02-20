package com.expensesadministrator.expenses.controller;

import com.expensesadministrator.expenses.dto.request.ExpenseCategoryRequestDto;
import com.expensesadministrator.expenses.dto.response.ExpenseCategoryResponseDto;
import com.expensesadministrator.expenses.entity.ExpenseCategory;
import com.expensesadministrator.expenses.exception.ExpenseCategoryNotFoundException;
import com.expensesadministrator.expenses.service.ExpenseCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    @PostMapping
    public ResponseEntity<ExpenseCategoryResponseDto> createCategory(@RequestBody ExpenseCategoryRequestDto expenseCategoryRequest) {
            ExpenseCategory existingCategory = expenseCategoryService.getCategoryByName(expenseCategoryRequest.name());
            ExpenseCategoryResponseDto categoryDto = expenseCategoryService.save(existingCategory);
            return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseCategoryResponseDto>> getAllCategories() {
        List<ExpenseCategoryResponseDto> categories = expenseCategoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ExpenseCategoryResponseDto> getCategoryByName(@PathVariable String name) {
        try {
            ExpenseCategoryResponseDto category = expenseCategoryService.getCategoryByNameDto(name);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (ExpenseCategoryNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping
    public ResponseEntity<ExpenseCategoryResponseDto> updateCategory(@RequestBody
                                                            ExpenseCategoryRequestDto expenseCategoryRequest) 
        {
        ExpenseCategory existingCategory = expenseCategoryService.getCategoryByName(expenseCategoryRequest.name());
        ExpenseCategoryResponseDto updatedCategory = expenseCategoryService.updateCategory(existingCategory);

        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }


    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        expenseCategoryService.deleteCategory(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}