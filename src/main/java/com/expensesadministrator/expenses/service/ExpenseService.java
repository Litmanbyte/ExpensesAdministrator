package com.expensesadministrator.expenses.service;

import com.expensesadministrator.expenses.dto.request.ExpenseRequestDto;
import com.expensesadministrator.expenses.dto.response.ExpenseResponseDto;
import com.expensesadministrator.expenses.dto.mapper.ExpenseMapper;
import com.expensesadministrator.expenses.entity.Expense;
import com.expensesadministrator.expenses.entity.ExpenseCategory;
import com.expensesadministrator.expenses.repository.ExpenseCategoryRepository;
import com.expensesadministrator.expenses.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return expenseMapper.toDto(expense);
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
        Optional<ExpenseCategory> nameOfCategory = expenseCategoryRepository.findByName(categoryName);

        List<Expense> expenses = getAllExpenses().stream()
                .filter(u -> u.getCategory().getName().equals(nameOfCategory))
                .collect(Collectors.toList());

        return expenses.stream()
                .map(ExpenseMapper::toDto)
                .collect(Collectors.toList());
    }

    // Busca uma despesa pelo ID
    public Optional<ExpenseResponseDto> getExpenseById(String id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        return expense.map(ExpenseMapper::toDto);
    }

    // Atualiza uma despesa pelo ID
    public Optional<ExpenseResponseDto> updateExpense(String id, Expense expense) {
        if (expenseRepository.existsById(id)) {
            expense.setId(id);
            Expense updatedExpense = expenseRepository.save(expense);
            return Optional.of(ExpenseMapper.toDto(updatedExpense));
        }
        return Optional.empty();  // Se não encontrar o ID
    }

    // Deleta uma despesa pelo ID
    public boolean deleteExpense(String id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;  // Se não encontrar o ID
    }

    public List<ExpenseResponseDto> getExpensesByName(String name) {
        List<Expense> expenses = expenseRepository.findByName(name);
        return expenses.stream()
                .map(ExpenseMapper::toDto)
                .collect(Collectors.toList());
    }
}

