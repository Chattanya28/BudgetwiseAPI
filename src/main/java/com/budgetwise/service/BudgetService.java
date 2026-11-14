package com.budgetwise.service;

import com.budgetwise.dto.BudgetRequest;
import com.budgetwise.entity.Budget;
import com.budgetwise.entity.User;
import com.budgetwise.repository.BudgetRepository;
import com.budgetwise.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetService(BudgetRepository budgetRepository, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }

    public Budget setBudget(String username, BudgetRequest request) {
        User user = userRepository.findByUsername(username).orElseThrow();

        Budget budget = budgetRepository.findByUserIdAndCategoryAndMonthAndYear(
                        user.getId(), request.getCategory(), request.getMonth(), request.getYear())
                .orElse(new Budget());

        budget.setUser(user);
        budget.setCategory(request.getCategory());
        budget.setMonthlyLimit(request.getMonthlyLimit());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());

        return budgetRepository.save(budget);
    }
}