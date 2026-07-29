package com.asim.finance.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardDto {

    private Double totalIncome;

    private Double totalExpense;

    private Double balance;

    private Integer totalTransactions;

    private Double budget;

    private Double remainingBudget;

    private Boolean budgetExceeded;

    private String budgetMessage;

    private Double budgetPercentage;
    private Map<String, Double> monthlyExpense;

}