package com.asim.finance.controller;
import com.asim.finance.entity.User;
//import ch.qos.logback.core.model.Model;
import com.asim.finance.service.UserService;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user,
                               RedirectAttributes redirectAttributes) {

        try {

            userService.registerUser(user);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Registration Successful. Please Login.");

            return "redirect:/login";

        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage());

            return "redirect:/register";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}