package com.asim.finance.service;


import com.asim.finance.entity.Income;
import com.asim.finance.entity.User;
import com.asim.finance.repository.IncomeRepository;

import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class IncomeServiceImpl
        implements IncomeService {


    private final IncomeRepository incomeRepository;


    public IncomeServiceImpl(
            IncomeRepository incomeRepository
    ){

        this.incomeRepository = incomeRepository;

    }



    @Override
    public Income saveIncome(
            Income income
    ){

        return incomeRepository.save(income);

    }



    @Override
    public List<Income> getUserIncome(
            User user
    ){

        return incomeRepository
                .findByUser(user);

    }



    @Override
    public Income getIncomeById(
            Long id
    ){

        return incomeRepository
                .findById(id)
                .orElse(null);

    }



    @Override
    public void deleteIncome(
            Long id
    ){

        incomeRepository.deleteById(id);

    }



    @Override
    public Double getTotalIncome(
            User user
    ){

        return incomeRepository
                .findByUser(user)
                .stream()
                .mapToDouble(
                        Income::getAmount
                )
                .sum();

    }


    @Override
    public double getAllIncome(){

        return incomeRepository
                .findAll()
                .stream()
                .mapToDouble(
                        Income::getAmount
                )
                .sum();

    }
}