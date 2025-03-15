package com.expensesadministrator.expenses.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Expense {

    @Id
    private String id;

    @DBRef
    private ExpenseCategory category;
    
    private double amount;
    private Date date;
    private Boolean paid;
    private Integer installment;

    @DBRef  
    private User user;  

}
