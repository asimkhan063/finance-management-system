package com.asim.finance.controller;


import com.asim.finance.entity.User;
import com.asim.finance.repository.UserRepository;
import com.asim.finance.service.EmailServiceImpl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.Random;



@Controller
public class ForgotPasswordController {



    private final UserRepository userRepository;

    private final EmailServiceImpl emailService;

    private final PasswordEncoder passwordEncoder;



    public ForgotPasswordController(
            UserRepository userRepository,
            EmailServiceImpl emailService,
            PasswordEncoder passwordEncoder
    ){

        this.userRepository=userRepository;
        this.emailService=emailService;
        this.passwordEncoder=passwordEncoder;

    }



    @GetMapping("/forgot-password")
    public String forgotPage(){

        return "forgot-password";

    }




    @PostMapping("/send-otp")
    public String sendOtp(
            @RequestParam String email,
            Model model
    ){


        User user =
                userRepository
                        .findByEmail(email)
                        .orElse(null);



        if(user==null){

            model.addAttribute(
                    "error",
                    "Email not found"
            );

            return "forgot-password";

        }



        String otp =
                String.valueOf(
                        new Random()
                                .nextInt(900000)
                                +100000
                );



        user.setResetOtp(otp);


        user.setOtpExpiry(
                LocalDateTime.now()
                        .plusMinutes(5)
        );


        userRepository.save(user);



        emailService.sendOtp(
                email,
                otp
        );


        model.addAttribute(
                "email",
                email
        );


        return "verify-otp";


    }




    @PostMapping("/verify-otp")
    public String verifyOtp(
            @RequestParam String email,
            @RequestParam String otp,
            Model model
    ){


        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();



        if(
                user.getResetOtp()
                        .equals(otp)

                        &&

                        user.getOtpExpiry()
                                .isAfter(LocalDateTime.now())
        ){


            model.addAttribute(
                    "email",
                    email
            );


            return "reset-password";

        }


        model.addAttribute(
                "error",
                "Invalid OTP"
        );


        return "verify-otp";


    }





    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String email,
            @RequestParam String password
    ){



        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();



        user.setPassword(
                passwordEncoder.encode(password)
        );


        user.setResetOtp(null);

        user.setOtpExpiry(null);



        userRepository.save(user);



        return "redirect:/login";


    }



}