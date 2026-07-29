package com.asim.finance.controller;


import com.asim.finance.entity.Expense;
import com.asim.finance.entity.User;
import com.asim.finance.service.ExpenseService;
import com.asim.finance.service.UserService;


import com.asim.finance.util.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import com.asim.finance.util.PdfUtil;
import com.asim.finance.util.PdfUtil;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
@RequestMapping("/expenses")
public class ExpenseController {


    private final ExpenseService expenseService;
    private final UserService userService;


    public ExpenseController(
            ExpenseService expenseService,
            UserService userService
    ) {

        this.expenseService = expenseService;
        this.userService = userService;

    }



    // Show all expenses
    @GetMapping
    public String viewExpenses(
            Authentication authentication,
            Model model
    ) {


        String email = authentication.getName();


        User user = userService
                .findByEmail(email)
                .get();


        model.addAttribute(
                "expenses",
                expenseService.getUserExpenses(user)
        );


        return "expenses";

    }




    // Add expense page
    @GetMapping("/add")
    public String addExpensePage(
            Model model
    ) {


        Expense expense = new Expense();

        expense.setDate(LocalDate.now());


        model.addAttribute(
                "expense",
                expense
        );


        return "add-expense";

    }





    // Save expense
    @PostMapping("/save")
    public String saveExpense(
            @ModelAttribute Expense expense,
            Authentication authentication
    ) {

        User user = userService
                .findByEmail(authentication.getName())
                .orElseThrow();

        expense.setUser(user);

        // ✅ Important
        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }

        expenseService.saveExpense(expense);

        return "redirect:/expenses";
    }




    // Delete expense
    @GetMapping("/delete/{id}")
    public String deleteExpense(
            @PathVariable Long id
    ) {


        expenseService.deleteExpense(id);


        return "redirect:/expenses";

    }
    // Edit expense page

    @GetMapping("/edit/{id}")
    public String editExpensePage(
            @PathVariable Long id,
            Model model
    ) {


        Expense expense = expenseService.getExpenseById(id);


        model.addAttribute(
                "expense",
                expense
        );


        return "edit-expense";

    }
    @PostMapping("/update")
    public String updateExpense(
            @ModelAttribute Expense expense,
            Authentication authentication
    ) {


        String email = authentication.getName();


        User user = userService
                .findByEmail(email)
                .get();


        expense.setUser(user);
        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }


        expenseService.saveExpense(expense);


        return "redirect:/expenses";

    }

    @GetMapping("/search")
    public String searchExpense(
            @RequestParam String category,
            Authentication authentication,
            Model model
    ){

        User user = userService
                .findByEmail(authentication.getName())
                .get();


        model.addAttribute(
                "expenses",
                expenseService.searchByCategory(
                        user,
                        category
                )
        );


        return "expenses";
    }

    @GetMapping("/pdf")
    public void downloadPdf(
            Authentication authentication,
            HttpServletResponse response
    ) throws Exception {

        User user =
                userService
                        .findByEmail(authentication.getName())
                        .orElseThrow();

        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=expense-report.pdf"
        );

        PdfUtil.generateExpensePdf(

                response.getOutputStream(),

                expenseService.getUserExpenses(user)

        );

    }

    @GetMapping("/excel")
    public void downloadExcel(
            Authentication authentication,
            HttpServletResponse response
    ) throws Exception {

        User user =
                userService
                        .findByEmail(authentication.getName())
                        .orElseThrow();

        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=expense-report.xlsx"
        );

        ExcelUtil.generateExpenseExcel(

                response.getOutputStream(),

                expenseService.getUserExpenses(user)

        );

    }



}