package com.asim.finance.rest;

import com.asim.finance.entity.Expense;
import com.asim.finance.entity.User;
import com.asim.finance.service.ExpenseService;
import com.asim.finance.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-expenses")
public class ExpenseApiController {

    private final ExpenseService expenseService;
    private final UserService userService;

    public ExpenseApiController(
            ExpenseService expenseService,
            UserService userService
    ) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @GetMapping
    public List<Expense> getExpenses(Authentication authentication) {

        User user = userService
                .findByEmail(authentication.getName())
                .orElseThrow();

        return expenseService.getUserExpenses(user);
    }

    @PostMapping
    public Expense saveExpense(
            @RequestBody Expense expense,
            Authentication authentication
    ) {

        User user = userService
                .findByEmail(authentication.getName())
                .orElseThrow();

        expense.setUser(user);

        return expenseService.saveExpense(expense);
    }
}