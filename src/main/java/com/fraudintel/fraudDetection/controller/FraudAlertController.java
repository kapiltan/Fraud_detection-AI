package com.fraudintel.fraudDetection.controller;

import com.fraudintel.fraudDetection.model.FraudAlert;
import com.fraudintel.fraudDetection.service.FraudAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FraudAlertController {
    @Autowired
    private FraudAlertService fraudAlertService;

    @GetMapping("/alerts")
    public List<FraudAlert> getAlerts(){
        return fraudAlertService.getAllAlerts();
    }
}
