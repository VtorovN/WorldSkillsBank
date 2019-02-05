package com.example.worldskills;

public class Account {

    private String type, number;
    private double balance;

    public Account(String type, String number, double balance) {
        this.type = type;
        this.number = number;
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
