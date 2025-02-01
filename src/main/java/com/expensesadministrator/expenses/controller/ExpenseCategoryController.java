package com.expensesadministrator.expenses.controller;

import com.expensesadministrator.expenses.dto.ExpenseCategoryResponseDto;
import com.expensesadministrator.expenses.entity.ExpenseCategory;
import com.expensesadministrator.expenses.service.ExpenseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expense-categories")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    @PostMapping
    public ResponseEntity<ExpenseCategoryResponseDto> createCategory(@RequestBody ExpenseCategory expenseCategory) {
            ExpenseCategoryResponseDto categoryDto = expenseCategoryService.save(expenseCategory);
            return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseCategoryResponseDto>> getAllCategories() {
        List<ExpenseCategoryResponseDto> categories = expenseCategoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseCategoryResponseDto> getCategoryById(@PathVariable String id) {
        Optional<ExpenseCategoryResponseDto> category = expenseCategoryService.getCategoryById(id);
        return category.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseCategoryResponseDto> updateCategory(@PathVariable String id,
                                                                     @RequestBody ExpenseCategory expenseCategory) {
        Optional<ExpenseCategoryResponseDto> updatedCategory = expenseCategoryService.updateCategory(id, expenseCategory);
        return updatedCategory.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        boolean isDeleted = expenseCategoryService.deleteCategory(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}