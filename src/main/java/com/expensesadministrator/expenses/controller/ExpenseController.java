package com.expensesadministrator.expenses.controller;

import com.expensesadministrator.expenses.dto.request.ExpenseRequestDto;
import com.expensesadministrator.expenses.dto.response.ExpenseResponseDto;
import com.expensesadministrator.expenses.entity.Expense;
import com.expensesadministrator.expenses.entity.User;
import com.expensesadministrator.expenses.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // Endpoint para salvar uma despesa
    @PostMapping("/expenses")
    public ResponseEntity<ExpenseResponseDto> saveExpense(@RequestBody ExpenseRequestDto expenseDto,
                                                           @AuthenticationPrincipal User authenticatedUser) {
        // Recupera o usuário autenticado a partir do token JWT
        String nameOfUser = authenticatedUser.getLogin();

        // Salva a despesa associada ao usuário
        ExpenseResponseDto savedExpense = expenseService.save(expenseDto, nameOfUser);

        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    // Endpoint para obter todas as despesas
    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpenses() {
        List<ExpenseResponseDto> expenses = expenseService.getAllExpensesDto();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    // Endpoint para buscar despesas por nome de usuário
    @GetMapping("/user/{nameOfUser}")
    public ResponseEntity<List<ExpenseResponseDto>> getExpensesByUser(@PathVariable String nameOfUser) {
        List<ExpenseResponseDto> expenses = expenseService.getExpensesByNameOfUser(nameOfUser);
        return expenses.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    // Endpoint para buscar despesas por nome da categoria
    @GetMapping("/category/{nameOfCategory}")
    public ResponseEntity<List<ExpenseResponseDto>> getExpensesByCategory(@PathVariable String nameOfCategory) {
        List<ExpenseResponseDto> expenses = expenseService.getExpensesByCategoryName(nameOfCategory);
        return expenses.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    // Endpoint para buscar uma despesa por ID
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> getExpenseById(@PathVariable String id) {
        ExpenseResponseDto expense = expenseService.getExpenseById(id);
        return new ResponseEntity<>(expense, HttpStatus.OK);

    }

    // Endpoint para atualizar uma despesa
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> updateExpense(@PathVariable String id, @RequestBody Expense expense) {
        ExpenseResponseDto updatedExpense = expenseService.updateExpense(id, expense);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }

    // Endpoint para excluir uma despesa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
