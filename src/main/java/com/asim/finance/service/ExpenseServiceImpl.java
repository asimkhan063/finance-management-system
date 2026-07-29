package com.asim.finance.service;


import com.asim.finance.entity.Expense;
import com.asim.finance.entity.User;
import com.asim.finance.exception.ResourceNotFoundException;
import com.asim.finance.repository.ExpenseRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
public class ExpenseServiceImpl implements ExpenseService {


    private final ExpenseRepository expenseRepository;


    public ExpenseServiceImpl(
            ExpenseRepository expenseRepository
    ){

        this.expenseRepository = expenseRepository;

    }



    @Override
    public Expense saveExpense(
            Expense expense
    ){

        return expenseRepository.save(expense);

    }



    @Override
    public List<Expense> getUserExpenses(
            User user
    ){

        return expenseRepository.findByUser(user);

    }



    @Override
    public Expense getExpenseById(
            Long id
    ){

        return expenseRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense Not Found"
                        ));

    }



    @Override
    public void deleteExpense(
            Long id
    ){

        expenseRepository.deleteById(id);

    }


    @Override
    public List<Expense> searchByCategory(
            User user,
            String category
    ){

        return expenseRepository
                .findByUserAndCategory(user, category);

    }

    @Override
    public List<Expense> searchByDate(
            User user,
            LocalDate date
    ){

        return expenseRepository
                .findByUserAndDate(user,date);

    }



    @Override
    public List<Expense> filterByMonth(
            User user,
            LocalDate start,
            LocalDate end
    ){

        return expenseRepository
                .findByUserAndDateBetween(
                        user,
                        start,
                        end
                );

    }
    @Override
    public Double getTotalExpense(User user) {


        return expenseRepository
                .findByUser(user)
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();

    }



    @Override
    public List<Expense> getRecentExpenses(User user) {


        return expenseRepository
                .findByUser(user)
                .stream()
                .sorted(
                        (e1,e2) ->
                                e2.getDate()
                                        .compareTo(e1.getDate())
                )
                .limit(5)
                .toList();

    }


    @Override
    public Map<String, Double> getCategoryWiseExpense(User user) {

        List<Object[]> result =
                expenseRepository.getCategoryWiseExpense(user);

        Map<String, Double> map = new LinkedHashMap<>();

        for (Object[] row : result) {

            map.put(
                    row[0].toString(),
                    ((Number) row[1]).doubleValue()
            );
        }

        return map;
    }

    @Override
    public Map<String, Double> getMonthlyExpense(User user) {


        List<Object[]> result =
                expenseRepository.getMonthlyExpense(user);


        Map<String, Double> map =
                new LinkedHashMap<>();


        String[] months = {

                "",
                "Jan",
                "Feb",
                "Mar",
                "Apr",
                "May",
                "Jun",
                "Jul",
                "Aug",
                "Sep",
                "Oct",
                "Nov",
                "Dec"

        };


        for(Object[] row : result){


            Integer month =
                    ((Number)row[0]).intValue();


            Double amount =
                    ((Number)row[1]).doubleValue();



            map.put(
                    months[month],
                    amount
            );

        }


        return map;

    }
    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public double getAllExpense(){

        return expenseRepository
                .findAll()
                .stream()
                .mapToDouble(
                        Expense::getAmount
                )
                .sum();

    }


}