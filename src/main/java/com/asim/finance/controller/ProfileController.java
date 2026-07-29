package com.asim.finance.controller;

import com.asim.finance.entity.User;
import com.asim.finance.service.DashboardService;
import com.asim.finance.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private final UserService userService;
    private final DashboardService dashboardService;

    public ProfileController(UserService userService,
                             DashboardService dashboardService) {

        this.userService = userService;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/profile")
    public String profile(
            Authentication authentication,
            Model model
    ) {

        User user = userService
                .findByEmail(authentication.getName())
                .orElseThrow();

        model.addAttribute("user", user);

        model.addAttribute(
                "dashboard",
                dashboardService.getDashboard(user)
        );

        return "profile";
    }

}