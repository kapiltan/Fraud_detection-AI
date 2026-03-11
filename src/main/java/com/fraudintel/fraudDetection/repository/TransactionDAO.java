package com.fraudintel.fraudDetection.repository;

import com.fraudintel.fraudDetection.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repositry to interact with mongodb
// save(), findById(), findAll(), deleteById()
@Repository
public class TransactionDAO {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Transaction insert(Transaction x) {
        return mongoTemplate.insert(x);
    }

    public List<Transaction> findAll() {
        return mongoTemplate.findAll(Transaction.class);
    }

    public Transaction findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Transaction.class);
    }

    public List<Transaction> findByUser(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Transaction.class);
    }

    public void save(Transaction tx) {
        mongoTemplate.save(tx);
    }

    // pagination using skip and limit

    public List<Transaction> findAll(int page, int size, String sortBy) {
        Query query = new Query();

        query.skip((long) page * size);
        query.limit(size);

        query.with(org.springframework.data.domain.Sort.by(
                org.springframework.data.domain.Sort.Direction.DESC, sortBy));

        return mongoTemplate.find(query, Transaction.class);
    }
}
