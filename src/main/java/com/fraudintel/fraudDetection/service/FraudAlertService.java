package com.fraudintel.fraudDetection.service;

import com.fraudintel.fraudDetection.model.FraudAlert;
import com.fraudintel.fraudDetection.repository.FraudAlertDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FraudAlertService {
    @Autowired
    private FraudAlertDAO fraudAlertDAO;

    public List<FraudAlert> getAllAlerts(){
        return fraudAlertDAO.findAll();
    }

}
