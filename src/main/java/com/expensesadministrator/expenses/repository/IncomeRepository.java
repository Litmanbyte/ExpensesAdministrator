package com.expensesadministrator.expenses.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.expensesadministrator.expenses.entity.Income;

public interface IncomeRepository extends MongoRepository<Income, String>{

}
