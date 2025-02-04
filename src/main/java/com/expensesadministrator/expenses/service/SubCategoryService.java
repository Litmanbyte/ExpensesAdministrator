package com.expensesadministrator.expenses.service;

import com.expensesadministrator.expenses.entity.ExpenseCategory;
import com.expensesadministrator.expenses.entity.SubCategory;
import com.expensesadministrator.expenses.repository.ExpenseCategoryRepository;
import com.expensesadministrator.expenses.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {


    private final SubCategoryRepository subCategoryRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository, ExpenseCategoryRepository expenseCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    // Create a new SubCategory
    public SubCategory createSubCategory(SubCategory subCategory) {
        if (subCategory.getParent() != null) {
            Optional<ExpenseCategory> parentCategory = expenseCategoryRepository.findById(subCategory.getParent().getId());
            if (parentCategory.isPresent()) {
                return subCategoryRepository.save(subCategory);
            }
            throw new RuntimeException("Parent category not found");
        }
        throw new RuntimeException("Parent category must be specified");
    }

    // Get all subcategories
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    // Get a subcategory by ID
    public Optional<SubCategory> getSubCategoryById(String id) {
        return subCategoryRepository.findById(id);
    }

    // Update an existing subcategory
    public SubCategory updateSubCategory(String id, SubCategory subCategoryDetails) {
        Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(id);
        if (subCategoryOptional.isPresent()) {
            SubCategory subCategory = subCategoryOptional.get();
            if (subCategoryDetails.getName() != null) {
                subCategory.setName(subCategoryDetails.getName());
            }
            if (subCategoryDetails.getParent() != null) {
                subCategory.setParent(subCategoryDetails.getParent());
            }
            return subCategoryRepository.save(subCategory);
        } else {
            throw new RuntimeException("SubCategory not found");
        }
    }

    // Delete a subcategory by ID
    public void deleteSubCategory(String id) {
        Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(id);
        if (subCategoryOptional.isPresent()) {
            subCategoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("SubCategory not found");
        }
    }

    // Get all subcategories for a specific parent category
    public List<SubCategory> getSubCategoriesByParentId(String parentId) {
        return subCategoryRepository.findByParentId(parentId);
    }
}
