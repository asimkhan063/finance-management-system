package com.asim.finance.service;

import com.asim.finance.entity.Budget;
import com.asim.finance.entity.User;
import com.asim.finance.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Budget saveBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public Optional<Budget> getBudget(
            User user,
            Integer month,
            Integer year
    ) {
        return budgetRepository.findByUserAndMonthAndYear(
                user,
                month,
                year
        );
    }
}