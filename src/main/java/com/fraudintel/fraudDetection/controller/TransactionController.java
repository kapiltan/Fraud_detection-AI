package com.fraudintel.fraudDetection.controller;

import com.fraudintel.fraudDetection.model.Transaction;
import com.fraudintel.fraudDetection.repository.TransactionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionDAO dao;
//    private KafkaTemplate<String, Object> kafkaTemplate;
//    private final String TOPIC = "transactions.raw";

    @PostMapping("/create")
    public Transaction createTx(@RequestBody Transaction tx){
        tx.setId(UUID.randomUUID().toString());
        Transaction saved=dao.insert(tx);
//        kafkaTemplate.send(TOPIC,saved.getId(),saved);
        return saved;
    }

    @GetMapping("/all")
    public List<Transaction>  getAll(){
        return dao.findAll();
    }
}
