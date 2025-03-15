package com.expensesadministrator.expenses.service;

import com.expensesadministrator.expenses.entity.Income;
import com.expensesadministrator.expenses.exception.IncomeNotFoundException;
import com.expensesadministrator.expenses.repository.IncomeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    // Criar (Salvar) um novo Income
    public Income createIncome(Income income) {
        return incomeRepository.save(income);
    }

    // Buscar Income por ID, lançando uma exceção personalizada se não encontrado
    public Income getIncomeById(String id) {
        return incomeRepository.findById(id)
                .orElseThrow(() -> new IncomeNotFoundException("Income not found with id " + id));
    }

    // Listar todos os Incomes
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    // Atualizar Income existente
    public Income updateIncome(String id, Income income) {
        // Verifica se o Income existe antes de atualizar
        if (incomeRepository.existsById(id)) {
            income.setId(id);  // Asegura que o ID não será perdido
            return incomeRepository.save(income);
        } else {
            throw new IncomeNotFoundException("Income not found with id " + id);
        }
    }

    // Excluir Income pelo ID
    public void deleteIncome(String id) {
        if (incomeRepository.existsById(id)) {
            incomeRepository.deleteById(id);
        } else {
            throw new IncomeNotFoundException("Income not found with id " + id);
        }
    }
}
