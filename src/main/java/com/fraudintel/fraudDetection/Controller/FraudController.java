package com.fraudintel.fraudDetection.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FraudController {
    @GetMapping("/fraud/all")
    public String getAllFrauds(){
        return "Fraud API is workiing.";
    }
}
