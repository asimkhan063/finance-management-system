package com.asim.finance.service;

import com.asim.finance.dto.DashboardDto;
import com.asim.finance.entity.User;

public interface DashboardService {

    DashboardDto getDashboard(User user);

}