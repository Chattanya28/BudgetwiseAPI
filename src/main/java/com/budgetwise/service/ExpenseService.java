package com.budgetwise.service;

import com.budgetwise.dto.ExpenseRequest;
import com.budgetwise.entity.Budget;
import com.budgetwise.entity.Expense;
import com.budgetwise.entity.User;
import com.budgetwise.repository.BudgetRepository;
import com.budgetwise.repository.ExpenseRepository;
import com.budgetwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;

    @Value("${budget.alert.threshold:0.80}")
    private double alertThreshold;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, BudgetRepository budgetRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.budgetRepository = budgetRepository;
    }

    public Expense addExpense(String username, ExpenseRequest request) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setCategory(request.getCategory());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setUser(user);
        return expenseRepository.save(expense);
    }

    // Ye method check karega ki budget cross hua ya nahi
    public Map<String, Object> checkBudgetAlert(String username, String categoryStr, int month, int year) {
        User user = userRepository.findByUsername(username).orElseThrow();

        // Enum convert
        com.budgetwise.entity.Category category = com.budgetwise.entity.Category.valueOf(categoryStr.toUpperCase());

        Budget budget = budgetRepository.findByUserIdAndCategoryAndMonthAndYear(
                user.getId(), category, month, year).orElse(null);

        Map<String, Object> response = new HashMap<>();
        if (budget == null) {
            response.put("status", "NO_BUDGET_SET");
            return response;
        }

        Double totalSpent = expenseRepository.sumByCategoryAndMonth(user.getId(), category, month, year);
        double percentageUsed = (totalSpent / budget.getMonthlyLimit());

        String status = "OK";
        if (percentageUsed >= 1.0) {
            status = "EXCEEDED";
        } else if (percentageUsed >= alertThreshold) {
            status = "WARNING";
        }

        response.put("category", category);
        response.put("totalSpent", totalSpent);
        response.put("budgetLimit", budget.getMonthlyLimit());
        response.put("percentageUsed", Math.round(percentageUsed * 100) + "%");
        response.put("status", status);

        return response;
    }
}