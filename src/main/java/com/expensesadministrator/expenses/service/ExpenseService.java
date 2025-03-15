package com.expensesadministrator.expenses.service;

import com.expensesadministrator.expenses.dto.request.ExpenseRequestDto;
import com.expensesadministrator.expenses.dto.response.ExpenseResponseDto;
import com.expensesadministrator.expenses.dto.mapper.ExpenseMapper;
import com.expensesadministrator.expenses.entity.Expense;
import com.expensesadministrator.expenses.entity.ExpenseCategory;
import com.expensesadministrator.expenses.entity.User;
import com.expensesadministrator.expenses.exception.ExpenseNotFoundException;
import com.expensesadministrator.expenses.repository.ExpenseRepository;
import com.expensesadministrator.expenses.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {


    private final ExpenseRepository expenseRepository;
    private final UserService userService;
    private final ExpenseMapper expenseMapper;
    private final ExpenseCategoryService expenseCategoryService;


    public ExpenseService(ExpenseCategoryService expenseCategoryService, ExpenseRepository expenseRepository, UserService userService, ExpenseMapper expenseMapper, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
        this.userService = userService;
        this.expenseCategoryService = expenseCategoryService;
    }

    public ExpenseResponseDto save(ExpenseRequestDto expenseDto, String userName) {
        User u = userService.getUserByName(userName);
        ExpenseCategory ec = expenseCategoryService.getCategoryByName(userName);
        Expense expense = new Expense(
            null,
            ec,
            expenseDto.amount(),
            new Date(),
            false,
            expenseDto.installment(),
            u);
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

