package com.budgetwise.dto;

import com.budgetwise.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ExpenseRequest {
    @NotNull
    @Positive
    private Double amount;

    @NotBlank
    private String description;

    @NotNull
    private Category category;

    @NotNull
    private LocalDate expenseDate;
}