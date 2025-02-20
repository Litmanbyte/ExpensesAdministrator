package com.expensesadministrator.expenses.dto.mapper;

import com.expensesadministrator.expenses.dto.request.SubCategoryRequestDto;
import com.expensesadministrator.expenses.dto.response.SubCategoryExpenseResponseDto;
import com.expensesadministrator.expenses.entity.SubCategory;

public class SubCategoryMapper {

    // Convert SubCategory entity to SubCategoryExpenseResponseDto
    public static SubCategoryExpenseResponseDto toDto(SubCategory subCategory) {
        String parentName = (subCategory.getParent() != null) ? subCategory.getParent().getName() : "No Parent";
        return new SubCategoryExpenseResponseDto(
                subCategory.getNameOfUser(),
                parentName
        );
    }

    // Convert SubCategoryRequestDto to SubCategory entity
    public static SubCategory toEntity(SubCategoryRequestDto subCategoryRequestDto) {
        return new SubCategory(
                null, // Assuming ID is auto-generated, this will be handled by MongoDB
                subCategoryRequestDto.name(), // Name from DTO
                null, // Name of User from DTO
                subCategoryRequestDto.parentName() // Parent category will be set later (probably from the service layer)
        );
    }
}
