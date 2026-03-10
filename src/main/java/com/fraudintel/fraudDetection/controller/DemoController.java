package com.fraudintel.fraudDetection.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class DemoController {
    @GetMapping("/transactions")
    public List<Map<String,Object>> demoTransactions(){
        return List.of(
                Map.of("id", "demo-1", "userId", "DemoUser", "amount", 2499, "timestamp", "2025-01-10 10:00"),
                Map.of("id", "demo-2", "userId", "DemoUser", "amount", 12000, "timestamp", "2025-01-11 12:00")
        );
    }

    @GetMapping("/alerts")
    public List<Map<String, Object>> demoAlerts(){
        return List.of(
                Map.of("userId", "DemoUser", "severity", "HIGH", "reason", "Unusual activity detected", "amount", 2499,"alertTime", "2025-01-10 10:00"),
                Map.of("userId", "DemoUser", "severity", "MEDIUM", "reason", "Login from new location","amount", 12000, "alertTime", "2025-01-11 12:00")
        );
    }
}
