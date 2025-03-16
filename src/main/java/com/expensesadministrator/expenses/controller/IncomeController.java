package com.expensesadministrator.expenses.controller;

import com.expensesadministrator.expenses.entity.Income;
import com.expensesadministrator.expenses.entity.User;
import com.expensesadministrator.expenses.service.IncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping
    public ResponseEntity<Income> createIncome(@RequestBody Income income, 
                                               @AuthenticationPrincipal User authenticatedUser) {
        // Associa a renda ao usuário autenticado
        income.setUser(authenticatedUser);
        
        Income createdIncome = incomeService.createIncome(income);
        return new ResponseEntity<>(createdIncome, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Income> getIncomeById(@PathVariable String id) {
        Income income = incomeService.getIncomeById(id);
        return new ResponseEntity<>(income, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Income> getIncomeById(@PathVariable String id, 
                                                @AuthenticationPrincipal User authenticatedUser) {
        Income income = incomeService.getIncomeById(id);

        // Verifica se o usuário autenticado é o dono da renda
        if (!income.getUser().getId().equals(authenticatedUser.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(income, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Income>> getAllIncomes() {
        List<Income> incomes = incomeService.getAllIncomes();
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Income> updateIncome(@PathVariable String id, @RequestBody Income income,
                                               @AuthenticationPrincipal User authenticatedUser) {
        Income existingIncome = incomeService.getIncomeById(id);

        // Garante que o usuário autenticado pode modificar apenas suas próprias rendas
        if (!existingIncome.getUser().getId().equals(authenticatedUser.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        income.setUser(authenticatedUser);
        Income updatedIncome = incomeService.updateIncome(id, income);
        return new ResponseEntity<>(updatedIncome, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable String id, 
                                             @AuthenticationPrincipal User authenticatedUser) {
        Income income = incomeService.getIncomeById(id);

        // Garante que o usuário autenticado pode deletar apenas suas próprias rendas
        if (!income.getUser().getId().equals(authenticatedUser.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        incomeService.deleteIncome(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
