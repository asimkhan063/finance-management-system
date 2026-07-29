package com.asim.finance.service;


import com.asim.finance.entity.Income;
import com.asim.finance.entity.User;

import java.util.List;


public interface IncomeService {


    Income saveIncome(Income income);


    List<Income> getUserIncome(User user);


    Income getIncomeById(Long id);


    void deleteIncome(Long id);


    Double getTotalIncome(User user);

    double getAllIncome();

}