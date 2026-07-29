package com.asim.finance.repository;


import com.asim.finance.entity.Income;
import com.asim.finance.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IncomeRepository
        extends JpaRepository<Income,Long> {


    List<Income> findByUser(User user);


}