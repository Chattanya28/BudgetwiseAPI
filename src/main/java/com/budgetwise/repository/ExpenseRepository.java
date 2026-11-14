package com.budgetwise.repository;

import com.budgetwise.entity.Expense;
import com.budgetwise.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);

    // 1. Alert ke liye: User ne ek month me ek category pe kitna kharch kiya
    @Query("""
        SELECT COALESCE(SUM(e.amount), 0) FROM Expense e
        WHERE e.user.id = :userId
          AND e.category = :category
          AND MONTH(e.expenseDate) = :month
          AND YEAR(e.expenseDate) = :year
    """)
    Double sumByCategoryAndMonth(Long userId, Category category, int month, int year);

    // 2. Monthly Summary ke liye: Kis category pe kitna total gaya
    @Query("""
        SELECT e.category, SUM(e.amount) FROM Expense e
        WHERE e.user.id = :userId
          AND MONTH(e.expenseDate) = :month
          AND YEAR(e.expenseDate) = :year
        GROUP BY e.category
    """)
    List<Object[]> summaryByCategory(Long userId, int month, int year);
}