package com.asim.finance.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDashboardDto {


    private long totalUsers;

    private double totalIncome;

    private double totalExpense;

}