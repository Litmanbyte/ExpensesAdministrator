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

    @ExceptionHandler(SubCategoryNotFoundException.class)
    public ResponseEntity<Void> handleSubCategoryNotFound(SubCategoryNotFoundException ex){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ParentCategoryNotFoundException.class)
    public ResponseEntity<Void> handleParentCategoryNotFoundException(ParentCategoryNotFoundException ex){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
