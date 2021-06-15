package com.example.testbot.exporter;

import com.example.testbot.model.Customer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelFileExporter {
    public static ByteArrayInputStream exportExcelFile(List<Customer> customers) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Customers");
            Row row = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // Creating header
            Cell cell = row.createCell(0);
            cell.setCellValue("First Name");
            cell.setCellStyle(headerCellStyle);
            cell = row.createCell(1);
            cell.setCellValue("Last Name");
            cell.setCellStyle(headerCellStyle);
            cell = row.createCell(2);
            cell.setCellValue("Mobile");
            cell.setCellStyle(headerCellStyle);
            cell = row.createCell(3);
            cell.setCellValue("Email");
            cell.setCellStyle(headerCellStyle);
            for (int i = 0; i < customers.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                dataRow.createCell(0).setCellValue(customers.get(i).getFirstName());
                dataRow.createCell(1).setCellValue(customers.get(i).getLastname());
                dataRow.createCell(2).setCellValue(customers.get(i).getModileNumber());
                dataRow.createCell(3).setCellValue(customers.get(i).getEmail());
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
