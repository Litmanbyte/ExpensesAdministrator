package com.expensesadministrator.expenses.dto.mapper;

import com.expensesadministrator.expenses.dto.response.ExpenseCategoryResponseDto;
import com.expensesadministrator.expenses.entity.ExpenseCategory;

public class ExpenseCategoryMapper {

    public static ExpenseCategoryResponseDto toDto(ExpenseCategory expenseCategory) {
        return new ExpenseCategoryResponseDto(
                expenseCategory.getName(),
                expenseCategory.getNameOfUser()
        );
    }
}
