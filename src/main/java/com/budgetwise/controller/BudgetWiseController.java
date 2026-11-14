package com.budgetwise.controller;

import com.budgetwise.dto.BudgetRequest;
import com.budgetwise.dto.ExpenseRequest;
import com.budgetwise.entity.Budget;
import com.budgetwise.entity.Expense;
import com.budgetwise.service.BudgetService;
import com.budgetwise.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/budgetwise")
public class BudgetWiseController {

    private final ExpenseService expenseService;
    private final BudgetService budgetService;

    public BudgetWiseController(ExpenseService expenseService, BudgetService budgetService) {
        this.expenseService = expenseService;
        this.budgetService = budgetService;
    }

    // Expense APIs
    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(Authentication auth, @Valid @RequestBody ExpenseRequest request) {
        return ResponseEntity.ok(expenseService.addExpense(auth.getName(), request));
    }

    @GetMapping("/expenses/alert")
    public ResponseEntity<Map<String, Object>> getAlert(
            Authentication auth,
            @RequestParam String category,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(expenseService.checkBudgetAlert(auth.getName(), category, month, year));
    }

    // Budget APIs
    @PostMapping("/budgets")
    public ResponseEntity<Budget> setBudget(Authentication auth, @Valid @RequestBody BudgetRequest request) {
        return ResponseEntity.ok(budgetService.setBudget(auth.getName(), request));
    }
}