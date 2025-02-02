package com.expensesadministrator.expenses.dto.mapper;

import com.expensesadministrator.expenses.dto.response.SubCategoryExpenseResponseDto;
import com.expensesadministrator.expenses.entity.SubCategory;

public class SubCategoryMapper {

    public static SubCategoryExpenseResponseDto toDto(SubCategory subCategory) {
        return new SubCategoryExpenseResponseDto(
                subCategory.getNameOfUser(),
                subCategory.getParent().getName()
        );
    }
}
