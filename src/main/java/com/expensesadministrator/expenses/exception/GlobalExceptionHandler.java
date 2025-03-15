package com.expensesadministrator.expenses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpenseCategoryNotFoundException.class)
    public ResponseEntity<Void> handleCategoryNotFound(ExpenseCategoryNotFoundException ex) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<Void> handleExpenseNotFound(ExpenseNotFoundException ex){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncomeNotFoundException.class)
    public ResponseEntity<Void> handleExpenseNotFound(IncomeNotFoundException ex){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Void> handleUserNotFound(UserNotFoundException ex){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
