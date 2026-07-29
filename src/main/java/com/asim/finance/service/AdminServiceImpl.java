package com.asim.finance.service;


import com.asim.finance.dto.AdminDashboardDto;
import com.asim.finance.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl
        implements AdminService {


    private final UserRepository userRepository;
    private final ExpenseService expenseService;
    private final IncomeService incomeService;



    public AdminServiceImpl(
            UserRepository userRepository,
            ExpenseService expenseService,
            IncomeService incomeService
    ){

        this.userRepository=userRepository;
        this.expenseService=expenseService;
        this.incomeService=incomeService;

    }



    @Override
    public AdminDashboardDto getDashboard(){


        long users =
                userRepository.count();


        double expense =
                expenseService.getAllExpense();


        double income =
                incomeService.getAllIncome();



        return AdminDashboardDto.builder()

                .totalUsers(users)

                .totalExpense(expense)

                .totalIncome(income)

                .build();


    }


}