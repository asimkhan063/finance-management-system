package com.asim.finance.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

import java.io.IOException;


@Service
public class EmailServiceImpl
        implements EmailService {


    private final JavaMailSender mailSender;


    public EmailServiceImpl(
            JavaMailSender mailSender
    ){

        this.mailSender = mailSender;

    }



    @Override
    public void sendReportEmail(
            String email,
            java.io.ByteArrayInputStream pdf
    ){


        try {


            MimeMessage message =
                    mailSender.createMimeMessage();


            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            message,
                            true
                    );


            helper.setTo(email);

            helper.setSubject(
                    "Personal Finance Report"
            );


            helper.setText(
                    """
                    Hello,

                    Your Finance Report is attached.

                    Thank you.
                    
                    Finance Management System
                    """
            );


            byte[] pdfBytes =
                    pdf.readAllBytes();



            helper.addAttachment(
                    "finance-report.pdf",
                    new ByteArrayResource(pdfBytes)
            );



            mailSender.send(message);


        }
        catch(Exception e){

            throw new RuntimeException(
                    "Email sending failed",
                    e
            );

        }


    }

    public void sendOtp(String email, String otp){


        SimpleMailMessage message = new SimpleMailMessage();


        message.setTo(email);

        message.setSubject("Password Reset OTP");


        message.setText(
                "Your OTP is : " + otp +
                        "\nOTP valid for 5 minutes."
        );


        mailSender.send(message);


    }
}