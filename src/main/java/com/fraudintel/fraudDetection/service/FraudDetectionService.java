package com.fraudintel.fraudDetection.service;

import com.fraudintel.fraudDetection.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class FraudDetectionService {
    public boolean isFraud(Transaction tx){
        return tx.getAmount() > 10000;
    }
}
