package com.expensesadministrator.expenses.dto.mapper;

import com.expensesadministrator.expenses.entity.Expense;
import com.expensesadministrator.expenses.dto.response.ExpenseResponseDto;
import com.expensesadministrator.expenses.service.ExpenseCategoryService;
import com.expensesadministrator.expenses.service.UserService;

public class ExpenseMapper {

    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseMapper(ExpenseCategoryService expenseCategoryService, UserService userService ) {
        this.expenseCategoryService = expenseCategoryService;
    }

    public static ExpenseResponseDto toDto(Expense expense) {
        return new ExpenseResponseDto(
                expense.getUser().getUsername(),
                expense.getCategory().getName(),
                expense.getAmount(),
                expense.getInstallment()
        );
    }



}
