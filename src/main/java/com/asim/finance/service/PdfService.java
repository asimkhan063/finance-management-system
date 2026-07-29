package com.asim.finance.service;

import com.asim.finance.entity.User;

import java.io.ByteArrayInputStream;

public interface PdfService {

    ByteArrayInputStream generateReport(User user);

}