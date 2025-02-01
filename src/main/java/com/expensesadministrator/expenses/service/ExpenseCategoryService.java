package com.expensesadministrator.expenses.service;

import com.expensesadministrator.expenses.dto.ExpenseCategoryResponseDto;
import com.expensesadministrator.expenses.dto.mapper.ExpenseCategoryMapper;
import com.expensesadministrator.expenses.entity.ExpenseCategory;
import com.expensesadministrator.expenses.repository.ExpenseCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseCategoryService {

    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseCategoryService(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    public ExpenseCategoryResponseDto save(ExpenseCategory expenseCategory) {
        if (expenseCategoryRepository.findByName(expenseCategory.getName()).isEmpty()) {
            expenseCategoryRepository.save(expenseCategory);
            return ExpenseCategoryMapper.toDto(expenseCategory);
        }
        else
            return null; // fazer excep
    }

    public List<ExpenseCategoryResponseDto> getAllCategories() {
        List<ExpenseCategory> categories = expenseCategoryRepository.findAll();
        return categories.stream()
                .map(ExpenseCategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ExpenseCategoryResponseDto> getCategoryById(String id) {
        Optional<ExpenseCategory> category = expenseCategoryRepository.findById(id);
        return category.map(ExpenseCategoryMapper::toDto);
    }

    public Optional<ExpenseCategoryResponseDto> updateCategory(String id, ExpenseCategory expenseCategory) {
        if (expenseCategoryRepository.existsById(id)) {
            expenseCategory.setId(id);
            ExpenseCategory updatedCategory = expenseCategoryRepository.save(expenseCategory);
            return Optional.of(ExpenseCategoryMapper.toDto(updatedCategory));
        }
        return Optional.empty();  // Se não encontrar o ID
    }

    public boolean deleteCategory(String id) {
        if (expenseCategoryRepository.existsById(id)) {
            expenseCategoryRepository.deleteById(id);
            return true;
        }
        return false;  // Se não encontrar o ID
    }
}
