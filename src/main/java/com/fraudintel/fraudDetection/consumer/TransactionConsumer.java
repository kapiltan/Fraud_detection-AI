package com.fraudintel.fraudDetection.consumer;

import com.fraudintel.fraudDetection.model.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    @KafkaListener(
            topics = "fraud-transactions",
            groupId = "fraud-group",
            containerFactory = "transactionKafkaListenerFactory"
    )
    public void consume(Transaction tx) {
        System.out.println("📥 Consumed from Kafka: " + tx);
    }
}
