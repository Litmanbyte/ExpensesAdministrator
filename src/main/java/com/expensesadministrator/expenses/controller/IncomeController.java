package com.expensesadministrator.expenses.controller;

import com.expensesadministrator.expenses.entity.Income;
import com.expensesadministrator.expenses.service.IncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    // Injeção de dependência do IncomeService
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    // Criar um novo Income
    @PostMapping
    public ResponseEntity<Income> createIncome(@RequestBody Income income) {
        Income createdIncome = incomeService.createIncome(income);
        return new ResponseEntity<>(createdIncome, HttpStatus.CREATED);
    }

    // Buscar Income por ID
    @GetMapping("/{id}")
    public ResponseEntity<Income> getIncomeById(@PathVariable String id) {
        Income income = incomeService.getIncomeById(id);
        return new ResponseEntity<>(income, HttpStatus.OK);
    }

    // Listar todos os Incomes
    @GetMapping
    public ResponseEntity<List<Income>> getAllIncomes() {
        List<Income> incomes = incomeService.getAllIncomes();
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }

    // Atualizar Income existente
    @PutMapping("/{id}")
    public ResponseEntity<Income> updateIncome(@PathVariable String id, @RequestBody Income income) {
        Income updatedIncome = incomeService.updateIncome(id, income);
        return new ResponseEntity<>(updatedIncome, HttpStatus.OK);
    }

    // Excluir Income pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable String id) {
        incomeService.deleteIncome(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
