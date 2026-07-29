package com.asim.finance.service;

import com.asim.finance.entity.Budget;
import com.asim.finance.entity.User;

import java.util.Optional;

public interface BudgetService {

    Budget saveBudget(Budget budget);

    Optional<Budget> getBudget(
            User user,
            Integer month,
            Integer year
    );

}