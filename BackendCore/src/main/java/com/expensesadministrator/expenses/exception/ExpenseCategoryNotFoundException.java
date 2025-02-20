package com.expensesadministrator.expenses.exception;

public class ExpenseCategoryNotFoundException extends RuntimeException {

    public ExpenseCategoryNotFoundException(String message) {
        super(message);
    }
}