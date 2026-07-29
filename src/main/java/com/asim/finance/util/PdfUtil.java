package com.asim.finance.util;

import com.asim.finance.entity.Expense;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;

import java.io.OutputStream;
import java.util.List;

public class PdfUtil {

    public static void generateExpensePdf(
            OutputStream outputStream,
            List<Expense> expenses
    ) throws Exception {

        PdfWriter writer =
                new PdfWriter(outputStream);

        PdfDocument pdf =
                new PdfDocument(writer);

        Document document =
                new Document(pdf);

        document.add(new Paragraph("Personal Finance Report"));

        Table table = new Table(4);

        table.addHeaderCell("Title");
        table.addHeaderCell("Amount");
        table.addHeaderCell("Category");
        table.addHeaderCell("Date");

        double total = 0;

        for (Expense expense : expenses) {

            table.addCell(expense.getTitle());

            table.addCell(
                    String.valueOf(expense.getAmount()));

            table.addCell(expense.getCategory());

            table.addCell(
                    expense.getDate().toString());

            total += expense.getAmount();

        }

        document.add(table);

        document.add(
                new Paragraph(
                        "Total Expense : ₹" + total));

        document.close();

    }

}