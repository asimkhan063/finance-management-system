package com.asim.finance.controller;


import com.asim.finance.entity.User;
import com.asim.finance.service.*;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/report")
public class ReportController {


    private final UserService userService;

    private final PdfService pdfService;

    private final EmailService emailService;



    public ReportController(
            UserService userService,
            PdfService pdfService,
            EmailService emailService
    ){

        this.userService=userService;
        this.pdfService=pdfService;
        this.emailService=emailService;

    }



    @GetMapping("/email")
    public String emailReport(
            Authentication authentication
    ){


        User user =
                userService
                        .findByEmail(authentication.getName())
                        .orElseThrow();



        var pdf =
                pdfService.generateReport(user);



        emailService.sendReportEmail(
                user.getEmail(),
                pdf
        );



        return "redirect:/dashboard?emailSent";

    }


}