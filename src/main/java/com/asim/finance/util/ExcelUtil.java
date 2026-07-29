package com.asim.finance.util;

import com.asim.finance.entity.Expense;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.util.List;

public class ExcelUtil {

    public static void generateExpenseExcel(
            OutputStream outputStream,
            List<Expense> expenses
    ) throws Exception {

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet =
                workbook.createSheet("Expenses");

        Row header =
                sheet.createRow(0);

        header.createCell(0).setCellValue("Title");
        header.createCell(1).setCellValue("Amount");
        header.createCell(2).setCellValue("Category");
        header.createCell(3).setCellValue("Date");

        int rowNum = 1;

        for (Expense expense : expenses) {

            Row row =
                    sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(expense.getTitle());

            row.createCell(1)
                    .setCellValue(expense.getAmount());

            row.createCell(2)
                    .setCellValue(expense.getCategory());

            row.createCell(3)
                    .setCellValue(
                            expense.getDate().toString()
                    );
        }

        workbook.write(outputStream);

        workbook.close();
    }

}