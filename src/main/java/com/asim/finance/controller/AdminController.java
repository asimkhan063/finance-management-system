package com.asim.finance.controller;


import com.asim.finance.entity.Role;
import com.asim.finance.entity.User;
import com.asim.finance.service.AdminService;
import com.asim.finance.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
@Controller
public class AdminController {


    private final AdminService adminService;
    private final UserService userService;

    public AdminController(
            AdminService adminService, UserService userService
    ){

        this.adminService=adminService;
        this.userService = userService;

    }



    @GetMapping("/admin/dashboard")
    public String dashboard(Model model){


        model.addAttribute(
                "dashboard",
                adminService.getDashboard()
        );


        return "admin-dashboard";


    }

    @GetMapping("/admin/users")
    public String users(Model model) {

        model.addAttribute(
                "users",
                userService.getAllUsers()
        );

        return "admin-users";
    }


    @GetMapping("/admin/user/delete/{id}")
    public String deleteUser(
            @PathVariable Long id,
            Authentication authentication
    ) {

        User loggedInUser = userService
                .findByEmail(authentication.getName())
                .orElseThrow();

        if (loggedInUser.getId().equals(id)) {

            return "redirect:/admin/users?error=self";

        }

        userService.deleteUser(id);

        return "redirect:/admin/users?deleted";

    }

    @GetMapping("/admin/user/role/{id}")
    public String changeRole(
            @PathVariable Long id
    ) {

        User user = userService.findById(id)
                .orElseThrow();

        if (user.getRole() == Role.USER) {

            user.setRole(Role.ADMIN);

        } else {

            user.setRole(Role.USER);

        }

        userService.save(user);

        return "redirect:/admin/users";
    }


    @GetMapping("/admin/users/search")
    public String users(
            @RequestParam(required = false) String keyword,
            Model model
    ) {

        model.addAttribute(
                "users",
                userService.searchUsers(keyword)
        );

        model.addAttribute(
                "keyword",
                keyword
        );

        return "admin-users";
    }

}