package com.expensesadministrator.expenses.repository;

import com.expensesadministrator.expenses.entity.SubCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubCategoryRepository extends MongoRepository<SubCategory, String> {
}
