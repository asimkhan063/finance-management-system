package com.asim.finance.repository;


import com.asim.finance.entity.Expense;
import com.asim.finance.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {


    List<Expense> findByUser(User user);


    List<Expense> findByUserAndCategory(
            User user,
            String category
    );


    List<Expense> findByUserAndDate(
            User user,
            LocalDate date
    );


    List<Expense> findByUserAndDateBetween(
            User user,
            LocalDate startDate,
            LocalDate endDate
    );
    @Query("""
SELECT e.category, SUM(e.amount)
FROM Expense e
WHERE e.user = :user
GROUP BY e.category
""")
    List<Object[]> getCategoryWiseExpense(@Param("user") User user);

    @Query("""
       SELECT MONTH(e.date), SUM(e.amount)
       FROM Expense e
       WHERE e.user = :user
       GROUP BY MONTH(e.date)
       ORDER BY MONTH(e.date)
       """)
    List<Object[]> getMonthlyExpense(
            @Param("user") User user
    );

}