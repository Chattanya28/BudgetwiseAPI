package com.budgetwise.repository;

import com.budgetwise.entity.Budget;
import com.budgetwise.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByUserIdAndCategoryAndMonthAndYear(
            Long userId, Category category, int month, int year);

    List<Budget> findByUserId(Long userId);
}