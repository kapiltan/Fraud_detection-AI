package com.fraudintel.fraudDetection.controller;

import com.fraudintel.fraudDetection.model.Transaction;
import com.fraudintel.fraudDetection.repository.TransactionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
@CrossOrigin(origins = "http://localhost:5173")
public class AnalyticsController {
    @Autowired
    private TransactionDAO dao;

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {

        List<Transaction> transactions = dao.findAll();

        int total = transactions.size();

        double totalAmount = transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();

        double avgAmount = total == 0 ? 0 : totalAmount / total;

        Map<String, Object> result = new HashMap<>();

        result.put("totalTransactions", total);
        result.put("totalAmount", totalAmount);
        result.put("averageAmount", avgAmount);

        return result;
    }
}
