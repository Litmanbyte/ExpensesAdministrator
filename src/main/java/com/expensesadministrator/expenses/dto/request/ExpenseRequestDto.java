package com.expensesadministrator.expenses.dto.request;

public record ExpenseRequestDto(Double amount,String categoryName, Integer installment) {
}
