package com.expensesadministrator.expenses.service;

import com.expensesadministrator.expenses.dto.response.ExpenseCategoryResponseDto;
import com.expensesadministrator.expenses.dto.mapper.ExpenseCategoryMapper;
import com.expensesadministrator.expenses.entity.ExpenseCategory;
import com.expensesadministrator.expenses.exception.ExpenseCategoryNotFoundException;
import com.expensesadministrator.expenses.repository.ExpenseCategoryRepository;
import org.springframework.stereotype.Service;

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
            throw new ExpenseCategoryNotFoundException("Categoria não encontrada: " + expenseCategory.getName());
    }

    public List<ExpenseCategoryResponseDto> getAllCategories() {
        List<ExpenseCategory> categories = expenseCategoryRepository.findAll();
        return categories.stream()
                .map(ExpenseCategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public ExpenseCategoryResponseDto getCategoryById(String id) {
        ExpenseCategory category = expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new ExpenseCategoryNotFoundException("Categoria não encontrada!"));
        
        return ExpenseCategoryMapper.toDto(category);
    }
    
    public ExpenseCategoryResponseDto updateCategory(ExpenseCategory expenseCategory) {
        // Verifica se a categoria com o ID existe
        ExpenseCategory existingCategory = expenseCategoryRepository.findById(expenseCategory.getId())
                .orElseThrow(() -> new ExpenseCategoryNotFoundException("Categoria não encontrada para atualização!"));
        
        // Atualiza a categoria (pode realizar outros ajustes se necessário, como validar o nome, etc)
        existingCategory.setName(expenseCategory.getName());  // Exemplo: Atualizando nome, adicione outros campos conforme necessário
        ExpenseCategory updatedCategory = expenseCategoryRepository.save(existingCategory);
    
        return ExpenseCategoryMapper.toDto(updatedCategory);
    }
    

   /* public Optional<ExpenseCategoryResponseDto> updateCategory(ExpenseCategory expenseCategory) {
        if (!expenseCategoryRepository.findByName(expenseCategory.getName()).isEmpty()) {
            expenseCategory.setId(expenseCategory.getId());
            ExpenseCategory updatedCategory = expenseCategoryRepository.save(expenseCategory);
            return Optional.of(ExpenseCategoryMapper.toDto(updatedCategory));
        }
        return Optional.empty();  // Se não encontrar o ID
    }*/

    public void deleteCategory(String name) {
        ExpenseCategory category = getCategoryByName(name); 
        expenseCategoryRepository.delete(category);
    } 
    
    public ExpenseCategoryResponseDto getCategoryByNameDto(String name) {
        ExpenseCategory category = getCategoryByName(name); 
        return ExpenseCategoryMapper.toDto(category);
    }    
    
    public ExpenseCategory getCategoryByName(String name) {
        return expenseCategoryRepository.findByName(name)
            .orElseThrow(() -> new ExpenseCategoryNotFoundException("Categoria não encontrada: " + name));
    }
    
}
