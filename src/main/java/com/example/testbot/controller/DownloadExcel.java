package com.example.testbot.controller;

import com.example.testbot.exporter.ExcelFileExporter;
import com.example.testbot.model.Customer;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class DownloadExcel {
    @RequestMapping
    public String index() {
        return "index";
    }

    @GetMapping("/download/customers.xlsx")
    public void downloadCsv(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=customers.xlsx");
        ByteArrayInputStream stream = ExcelFileExporter.exportExcelFile(createTestData());
        IOUtils.copy(stream, response.getOutputStream());
    }

    private List<Customer> createTestData() {
        List<Customer> customers = new ArrayList<Customer>();
        for (Customer customer : Arrays.asList(new Customer("Vernon", "Barlow", "0123456789", "test1@simplesolution.dev"),
                new Customer("Maud", "Brock", "0123456788", "test2@simplesolution.dev"),
                new Customer("Chyna", "Cowan", "0123456787", "test3@simplesolution.dev"),
                new Customer("Krisha", "Tierney", "0123456786", "test4@simplesolution.dev"),
                new Customer("Sherry", "Rosas", "0123456785", "test5@simplesolution.dev"))) {
            customers.add(customer);
        }
        return customers;
    }
}
