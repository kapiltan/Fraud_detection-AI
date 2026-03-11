package com.fraudintel.fraudDetection.controller;

import com.fraudintel.fraudDetection.model.Transaction;
import com.fraudintel.fraudDetection.repository.TransactionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {
    @Autowired
    private TransactionDAO dao;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    private final String TOPIC = "fraud-transactions";

    @PostMapping("/create")
    public Transaction createTx(@RequestBody Transaction tx) {
        tx.setId(UUID.randomUUID().toString());
        tx.setTimestamp(LocalDateTime.now());
        Transaction saved = dao.insert(tx);
        kafkaTemplate.send(TOPIC, saved.getId(), saved);
        System.out.println("📤 Sent to Kafka: " + saved);
        return saved;
    }

    @GetMapping("/all")
    public List<Transaction> getAll() {
        System.out.println("Fetching transactions...");
        List<Transaction> transaction = dao.findAll();
        System.out.println("Transactions found: " + transaction.size());
        return transaction;
    }

    @GetMapping
    public List<Transaction> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "timestamp") String sort) {

        System.out.println("Fetching page: " + page);

        return dao.findAll(page, size, sort);
    }
}
