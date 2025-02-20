package com.expensesadministrator.expenses.exception;

public class ParentCategoryNotFoundException extends RuntimeException {

    public ParentCategoryNotFoundException(String message) {
        super(message);
    }
}
