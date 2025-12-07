package com.fraudintel.fraudDetection.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "fraud_alerts")
public class FraudAlert {
    @Id
    private String id;
    private String transactionId;
    private String userId;
    private double amount;
    private LocalDateTime alertTime;
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(LocalDateTime alertTime) {
        this.alertTime = alertTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public FraudAlert(String transactionId, String userId, double amount, String reason){
        this.transactionId=transactionId;
        this.userId=userId;
        this.amount=amount;
        this.reason=reason;
        this.alertTime = LocalDateTime.now();
    }

    @Override
    public String toString(){
        return "FraudAlert{" +
                "transactionId='" + transactionId + '\'' +
                ", userId='" + userId + '\'' +
                ", amount=" + amount +
                ", reason='" + reason + '\'' +
                ", alertTime=" + alertTime +
                '}';
    }
}
