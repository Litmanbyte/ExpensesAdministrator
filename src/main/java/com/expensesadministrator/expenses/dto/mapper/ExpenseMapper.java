package com.expensesadministrator.expenses.dto.mapper;

import com.expensesadministrator.expenses.entity.Expense;
import com.expensesadministrator.expenses.dto.ExpenseResponseDto;

public class ExpenseMapper {

    public static ExpenseResponseDto toDTO(Expense expense) {
        return new ExpenseResponseDto(
                expense.getNameOfUser(),
                expense.getCategory().getName(),
                expense.getAmount()
        );
    }
}
