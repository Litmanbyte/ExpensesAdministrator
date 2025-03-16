package com.expensesadministrator.expenses.dto.request;

import com.expensesadministrator.expenses.entity.UserRole;

public record RegisterRequestDTO(String login, String password, UserRole role) {
}
