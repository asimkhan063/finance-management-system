/**package com.asim.finance.service;

import com.asim.finance.dto.DashboardDto;
import com.asim.finance.entity.User;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl
        implements DashboardService {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;

    public DashboardServiceImpl(
            ExpenseService expenseService,
            IncomeService incomeService
    ){

        this.expenseService = expenseService;
        this.incomeService = incomeService;

    }

    @Override
    public DashboardDto getDashboard(User user){

        Double income =
                incomeService.getTotalIncome(user);

        Double expense =
                expenseService.getTotalExpense(user);

        Double balance =
                income-expense;

        int totalTransaction =
                incomeService
                        .getUserIncome(user)
                        .size()

                        +

                        expenseService
                                .getUserExpenses(user)
                                .size();

        return DashboardDto.builder()

                .totalIncome(income)

                .totalExpense(expense)

                .balance(balance)

                .totalTransactions(totalTransaction)

                .build();

    }

}*/
package com.asim.finance.service;

import com.asim.finance.dto.DashboardDto;
import com.asim.finance.entity.Budget;
import com.asim.finance.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;
    private final BudgetService budgetService;

    public DashboardServiceImpl(
            ExpenseService expenseService,
            IncomeService incomeService,
            BudgetService budgetService
    ) {

        this.expenseService = expenseService;
        this.incomeService = incomeService;
        this.budgetService = budgetService;

    }

    @Override
    public DashboardDto getDashboard(User user) {

        Double income = incomeService.getTotalIncome(user);

        if (income == null) {
            income = 0.0;
        }

        Double expense = expenseService.getTotalExpense(user);

        if (expense == null) {
            expense = 0.0;
        }

        Double balance = income - expense;

        int totalTransaction =
                incomeService.getUserIncome(user).size()
                        +
                        expenseService.getUserExpenses(user).size();

        // =========================
        // Budget Calculation
        // =========================

        LocalDate now = LocalDate.now();

        Optional<Budget> budget =
                budgetService.getBudget(
                        user,
                        now.getMonthValue(),
                        now.getYear()
                );

        double budgetAmount =
                budget.map(Budget::getAmount)
                        .orElse(0.0);

        double remainingBudget =
                budgetAmount - expense;

        boolean budgetExceeded =
                expense > budgetAmount && budgetAmount > 0;

        // =========================
        // Budget Percentage
        // =========================

        double percentage = 0;

        if (budgetAmount > 0) {

            percentage =
                    (expense / budgetAmount) * 100;

        }

        // =========================
        // Budget Message
        // =========================

        String message;

        if (budgetAmount == 0) {

            message = "No budget set.";

        } else if (expense > budgetAmount) {

            message = "Budget Exceeded";

        } else if (percentage >= 80) {

            message = "Warning! Budget is almost full.";

        } else {

            message = "Budget is under control.";

        }

        // =========================
        // Return Dashboard DTO
        // =========================
        // Monthly Expense Chart Data

        Map<String, Double> monthlyExpense =
                expenseService.getMonthlyExpense(user);
        return DashboardDto.builder()

                .totalIncome(income)

                .totalExpense(expense)

                .balance(balance)

                .totalTransactions(totalTransaction)

                .budget(budgetAmount)

                .remainingBudget(remainingBudget)

                .budgetExceeded(budgetExceeded)

                .budgetPercentage(percentage)

                .budgetMessage(message)

                .monthlyExpense(monthlyExpense)

                .build();

    }
}