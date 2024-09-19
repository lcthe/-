package org.dx.accountbalancemanager.entity;


public class AccountBalance {
    private int accountId;
    private int balance;
//    private int version;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

//    public int getVersion() {return version;}
//
//    public void setVersion(int version) {this.version = version;}
}
