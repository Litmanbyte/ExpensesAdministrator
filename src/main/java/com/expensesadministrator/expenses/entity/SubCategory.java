package com.expensesadministrator.expenses.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Document
public class SubCategory extends ExpenseCategory{

    private ExpenseCategory parent;
}
