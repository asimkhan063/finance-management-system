package com.asim.finance.controller;

import com.asim.finance.entity.Budget;
import com.asim.finance.entity.User;
import com.asim.finance.service.BudgetService;
import com.asim.finance.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/budget")
public class BudgetController {

    private final BudgetService budgetService;
    private final UserService userService;

    public BudgetController(
            BudgetService budgetService,
            UserService userService
    ) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @GetMapping
    public String budgetPage(
            Authentication authentication,
            Model model
    ) {

        User user = userService
                .findByEmail(authentication.getName())
                .orElseThrow();

        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();

        Budget budget = budgetService
                .getBudget(user, month, year)
                .orElse(new Budget());

        model.addAttribute("budget", budget);

        return "budget";
    }

    @PostMapping("/save")
    public String saveBudget(
            @ModelAttribute Budget budget,
            Authentication authentication
    ) {

        User user = userService
                .findByEmail(authentication.getName())
                .orElseThrow();

        budget.setUser(user);

        if (budget.getMonth() == null) {
            budget.setMonth(LocalDate.now().getMonthValue());
        }

        if (budget.getYear() == null) {
            budget.setYear(LocalDate.now().getYear());
        }

        budgetService.saveBudget(budget);

        return "redirect:/budget";
    }

}