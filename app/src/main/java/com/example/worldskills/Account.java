package com.example.worldskills;

public class Account {
    private String accountNumber, accountName;
    private long balance;

    public Account(String accountNumber, String accountName, long balance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public long getBalance() {
        return balance;
    }
}
