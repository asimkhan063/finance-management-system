package com.asim.finance.controller;

import com.asim.finance.dto.DashboardDto;
import com.asim.finance.entity.User;
import com.asim.finance.service.DashboardService;
import com.asim.finance.service.ExpenseService;
import com.asim.finance.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class DashboardController {

    private final UserService userService;
    private final ExpenseService expenseService;
    private final DashboardService dashboardService;

    public DashboardController(
            UserService userService,
            ExpenseService expenseService,
            DashboardService dashboardService
    ) {
        this.userService = userService;
        this.expenseService = expenseService;
        this.dashboardService = dashboardService;
    }

    // Dashboard
    @GetMapping("/dashboard")
    public String dashboard(
            Authentication authentication,
            Model model
    ) {

        User user = userService
                .findByEmail(authentication.getName())

                .orElseThrow(() -> new RuntimeException("User not found"));
               // .get();

        DashboardDto dashboard =
                dashboardService.getDashboard(user);

        model.addAttribute("dashboard", dashboard);

        model.addAttribute(
                "recentExpenses",
                expenseService.getRecentExpenses(user)
        );

        model.addAttribute(
                "categoryData",
                expenseService.getCategoryWiseExpense(user)
        );

        return "dashboard";
    }


    // Monthly Report
    @GetMapping("/monthly-report")
    public String monthlyReport(
            @RequestParam int month,
            @RequestParam int year,
            Authentication authentication,
            Model model
    ) {

        User user = userService
                .findByEmail(authentication.getName())

                .orElseThrow(() -> new RuntimeException("User not found"));
                //.get();

        LocalDate start = LocalDate.of(year, month, 1);

        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        model.addAttribute(
                "expenses",
                expenseService.filterByMonth(
                        user,
                        start,
                        end
                )
        );

        model.addAttribute("month", month);
        model.addAttribute("year", year);

        return "monthly-report";
    }

}