package com.fraudintel.fraudDetection.repository;

import com.fraudintel.fraudDetection.model.FraudAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FraudAlertDAO {
    @Autowired
    private MongoTemplate mongoTemplate;
    public void save(FraudAlert alert){
        mongoTemplate.save(alert);
    }

    public List<FraudAlert> findAll(){
        return mongoTemplate.findAll(FraudAlert.class);
    }
}
