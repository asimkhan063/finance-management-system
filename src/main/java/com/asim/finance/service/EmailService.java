package com.asim.finance.service;


import java.io.ByteArrayInputStream;

public interface EmailService {


    void sendReportEmail(
            String email,
            ByteArrayInputStream pdf
    );


}