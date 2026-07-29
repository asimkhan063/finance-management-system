package com.asim.finance.controller;

import com.asim.finance.entity.Income;
import com.asim.finance.entity.User;
import com.asim.finance.service.IncomeService;
import com.asim.finance.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/income")
public class IncomeController {

    private final IncomeService incomeService;
    private final UserService userService;

    public IncomeController(IncomeService incomeService,
                            UserService userService) {
        this.incomeService = incomeService;
        this.userService = userService;
    }

    // Show all income
    @GetMapping
    public String incomePage(Authentication authentication,
                             Model model) {

        User user = userService
                .findByEmail(authentication.getName())
                .orElseThrow();

        model.addAttribute(
                "incomes",
                incomeService.getUserIncome(user));

        return "income";
    }

    // Add income page
    @GetMapping("/add")
    public String addIncomePage(Model model) {

        Income income = new Income();
        income.setDate(LocalDate.now());

        model.addAttribute("income", income);

        return "add-income";
    }

    // Save income
    @PostMapping("/save")
    public String saveIncome(@ModelAttribute Income income,
                             Authentication authentication) {

        User user = userService
                .findByEmail(authentication.getName())
                .orElseThrow();

        income.setUser(user);

        incomeService.saveIncome(income);

        return "redirect:/income";
    }

    // Delete income
    @GetMapping("/delete/{id}")
    public String deleteIncome(@PathVariable Long id) {

        incomeService.deleteIncome(id);

        return "redirect:/income";
    }
}