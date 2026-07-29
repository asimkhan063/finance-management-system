package com.asim.finance.controller.api;


import com.asim.finance.entity.Expense;
import com.asim.finance.service.ExpenseService;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/expenses")
public class ExpenseRestController {


    private final ExpenseService expenseService;


    public ExpenseRestController(
            ExpenseService expenseService
    ){

        this.expenseService = expenseService;

    }



    // GET ALL EXPENSES

    @GetMapping
    public List<Expense> getAllExpenses(){

        return expenseService.getAllExpenses();

    }



    // SAVE EXPENSE

    @PostMapping
    public Expense saveExpense(
            @RequestBody Expense expense
    ){

        return expenseService.saveExpense(expense);

    }



    // DELETE EXPENSE

    @DeleteMapping("/{id}")
    public String deleteExpense(
            @PathVariable Long id
    ){

        expenseService.deleteExpense(id);

        return "Expense Deleted Successfully";

    }


}