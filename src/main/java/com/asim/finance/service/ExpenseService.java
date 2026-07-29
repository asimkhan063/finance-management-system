package com.asim.finance.service;


import com.asim.finance.entity.Expense;
import com.asim.finance.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface ExpenseService {


    Expense saveExpense(Expense expense);


    List<Expense> getUserExpenses(User user);


    Expense getExpenseById(Long id);

    Map<String, Double> getMonthlyExpense(User user);

    void deleteExpense(Long id);

    Double getTotalExpense(User user);
    double getAllExpense();


    Map<String, Double> getCategoryWiseExpense(User user);
    List<Expense> getRecentExpenses(User user);

    List<Expense> searchByCategory(
            User user,
            String category
    );


    List<Expense> searchByDate(
            User user,
            LocalDate date
    );


    List<Expense> filterByMonth(
            User user,
            LocalDate start,
            LocalDate end
    );

    List<Expense> getAllExpenses();

}