package com.expensesadministrator.expenses.repository;

import com.expensesadministrator.expenses.entity.SubCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubCategoryRepository extends MongoRepository<SubCategory, String> {
    List<SubCategory> findByParentId(String parentId);
}
