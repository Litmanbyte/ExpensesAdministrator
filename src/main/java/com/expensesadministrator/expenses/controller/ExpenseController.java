package com.expensesadministrator.expenses.controller;

import com.expensesadministrator.expenses.dto.request.ExpenseRequestDto;
import com.expensesadministrator.expenses.dto.response.ExpenseResponseDto;
import com.expensesadministrator.expenses.entity.Expense;
import com.expensesadministrator.expenses.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // Endpoint para salvar uma despesa
    @PostMapping
    public ResponseEntity<ExpenseResponseDto> saveExpense(@RequestBody ExpenseRequestDto expenseDto) {
        // Exemplo de como recuperar o nome do usuário (aqui é só um exemplo, deve ser recuperado de autenticação ou sessão)
        String nameOfUser = "João"; // Isso deve vir de algum contexto, como um JWT ou um serviço de autenticação.

        // Chama o serviço passando o nome do usuário
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
