package com.expensesadministrator.expenses.dto.mapper;

import com.expensesadministrator.expenses.dto.request.ExpenseRequestDto;
import com.expensesadministrator.expenses.entity.Expense;
import com.expensesadministrator.expenses.dto.response.ExpenseResponseDto;
import com.expensesadministrator.expenses.service.ExpenseCategoryService;

import java.util.Date;

public class ExpenseMapper {

    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseMapper(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    public static ExpenseResponseDto toDto(Expense expense) {
        return new ExpenseResponseDto(
                expense.getNameOfUser(),
                expense.getCategory().getName(),
                expense.getAmount()
        );
    }

    public Expense toEntity(ExpenseRequestDto dto, String nameOfUser) {
        // Preenchendo o nome do usuário, a categoria e a data automaticamente
        return new Expense(
                null, // ID será gerado automaticamente pelo banco
                nameOfUser, // Nome do usuário (passado como argumento)
                expenseCategoryService.getCategoryByName(dto.categoryName())
                        .orElseThrow(() -> new RuntimeException("Categoria não encontrada")),
                dto.amount(),
                new Date() // Data atual
        );
    }


}
