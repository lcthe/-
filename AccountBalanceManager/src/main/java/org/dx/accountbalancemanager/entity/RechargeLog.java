package org.dx.accountbalancemanager.entity;

import java.time.LocalDateTime;

public class RechargeLog {
    private int fromAccountId;
    private int toAccountId;
    private int balance;
    private LocalDateTime timestamp;
    private String type;

    public RechargeLog(int fromAccountId, int toAccountId, int balance, LocalDateTime timestamp, String type) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.balance = balance;
        this.timestamp = timestamp;
        this.type = type;
    }

    public int getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(int fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
