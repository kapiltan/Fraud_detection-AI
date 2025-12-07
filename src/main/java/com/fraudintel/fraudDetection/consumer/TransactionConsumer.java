package com.fraudintel.fraudDetection.consumer;

import com.fraudintel.fraudDetection.model.FraudAlert;
import com.fraudintel.fraudDetection.model.Transaction;
import com.fraudintel.fraudDetection.repository.FraudAlertDAO;
import com.fraudintel.fraudDetection.repository.TransactionDAO;
import com.fraudintel.fraudDetection.service.FraudDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    @Autowired
    private FraudDetectionService fraudService;

    @Autowired
    private TransactionDAO transactionRepository;

    @Autowired
    private FraudAlertDAO fraudAlertDAO;

    @KafkaListener(
            topics = "fraud-transactions",
            groupId = "fraud-group",
            containerFactory = "transactionKafkaListenerFactory"
    )
    public void consume(Transaction tx) {
        System.out.println("📥 Consumed from Kafka: " + tx);

        // Save transaction to DB
        transactionRepository.save(tx);
        System.out.println("💾 Saved to MongoDB: " + tx);

        if(fraudService.isFraud(tx)){
            FraudAlert alert = new FraudAlert(tx.getId(),tx.getUserId(),tx.getAmount(),"High Amount or Suspicious Spike");
            fraudAlertDAO.save(alert);
            System.out.println("ALERT: Fraud Detected ->" + tx.getUserId());
        }
        else{
            System.out.println("✔️ Transaction looks normal");
        }
    }
}
