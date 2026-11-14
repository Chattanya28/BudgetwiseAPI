package com.budgetwise.dto;

import com.budgetwise.entity.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BudgetRequest {
    @NotNull
    private Category category;

    @NotNull
    @Positive
    private Double monthlyLimit;

    @NotNull
    @Min(1) @Max(12)
    private Integer month;

    @NotNull
    private Integer year;
}