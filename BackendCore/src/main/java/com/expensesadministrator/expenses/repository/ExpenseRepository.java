package com.expensesadministrator.expenses.repository;

import com.expensesadministrator.expenses.entity.Expense;
import com.expensesadministrator.expenses.entity.ExpenseCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    List<Expense> findByNameOfUser(String nameOfUser);

    List<Expense> findByCategoryName(String categoryName);

    List<Expense> findByName(String name);

}
