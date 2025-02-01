package com.expensesadministrator.expenses.repository;


import com.expensesadministrator.expenses.entity.ExpenseCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ExpenseCategoryRepository extends MongoRepository<ExpenseCategory, String> {
    Optional<ExpenseCategory> findByName(String name);
}
