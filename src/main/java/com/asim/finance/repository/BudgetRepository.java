package com.asim.finance.repository;

import com.asim.finance.entity.Budget;
import com.asim.finance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByUserAndMonthAndYear(
            User user,
            Integer month,
            Integer year
    );

}