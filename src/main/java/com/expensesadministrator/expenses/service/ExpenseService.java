package com.expensesadministrator.expenses.service;

import com.expensesadministrator.expenses.dto.request.ExpenseRequestDto;
import com.expensesadministrator.expenses.dto.response.ExpenseResponseDto;
import com.expensesadministrator.expenses.dto.mapper.ExpenseMapper;
import com.expensesadministrator.expenses.entity.Expense;
import com.expensesadministrator.expenses.exception.ExpenseNotFoundException;
import com.expensesadministrator.expenses.repository.ExpenseCategoryRepository;
import com.expensesadministrator.expenses.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpenseMapper expenseMapper;


    public ExpenseService(ExpenseRepository expenseRepository, ExpenseCategoryRepository expenseCategoryRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    public ExpenseResponseDto save(ExpenseRequestDto expenseDto, String userName) {
        Expense expense = expenseMapper.toEntity(expenseDto, userName);
        expenseRepository.save(expense);
        return ExpenseMapper.toDto(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public List<ExpenseResponseDto> getAllExpensesDto() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
                .map(ExpenseMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ExpenseResponseDto> getExpensesByNameOfUser(String nameOfUser) {
        List<Expense> expenses = expenseRepository.findByNameOfUser(nameOfUser);
        return expenses.stream()
                .map(ExpenseMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ExpenseResponseDto> getExpensesByCategoryName(String categoryName) {
        List<Expense> expenses = expenseRepository.findByCategoryName(categoryName);
        
        if (expenses.isEmpty()) {
            throw new ExpenseNotFoundException("Gastos não encontrados para a categoria: " + categoryName);
        }
        
        return expenses.stream()
                .map(ExpenseMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ExpenseResponseDto> getExpensesByName(String name) {
        List<Expense> expenses = expenseRepository.findByName(name);
        return expenses.stream()
                .map(ExpenseMapper::toDto)
                .collect(Collectors.toList());
    }

    public ExpenseResponseDto getExpenseById(String id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Gasto não encontrado!"));
        return ExpenseMapper.toDto(expense);
    }

    public ExpenseResponseDto updateExpense(String id, Expense expense) {
        if (!expenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException("Gasto não encontrado para atualização!");
        }
        expense.setId(id);  // Garantir que o ID seja preservado
        Expense updatedExpense = expenseRepository.save(expense);
        return ExpenseMapper.toDto(updatedExpense);
    }

    public void deleteExpense(String id) {
        if (!expenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException("Gasto não encontrado para exclusão!");
        }
        expenseRepository.deleteById(id);
    }

}

