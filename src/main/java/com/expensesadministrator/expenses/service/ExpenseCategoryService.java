package com.expensesadministrator.expenses.service;

import com.expensesadministrator.expenses.dto.response.ExpenseCategoryResponseDto;
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
            throw new RuntimeException("Erro ao tentar criar a categoria.");
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

    public Optional<ExpenseCategoryResponseDto> updateCategory(ExpenseCategory expenseCategory) {
        if (!expenseCategoryRepository.findByName(expenseCategory.getName()).isEmpty()) {
            expenseCategory.setId(expenseCategory.getId());
            ExpenseCategory updatedCategory = expenseCategoryRepository.save(expenseCategory);
            return Optional.of(ExpenseCategoryMapper.toDto(updatedCategory));
        }
        return Optional.empty();  // Se não encontrar o ID
    }

    public void deleteCategory(String name) {
        try {
            getCategoryByName(name).ifPresent(expenseCategoryRepository::delete);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar excluir a categoria.");
        }
    }

    public Optional<ExpenseCategoryResponseDto> getCategoryByNameDto(String name) {
        Optional<ExpenseCategory> category = expenseCategoryRepository.findByName(name);
        return category.map(ExpenseCategoryMapper::toDto);
    }

    public Optional<ExpenseCategory> getCategoryByName(String name) {
        Optional<ExpenseCategory> category = expenseCategoryRepository.findByName(name);
        return category;
    }
}
