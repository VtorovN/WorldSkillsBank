package com.example.worldskills;

public class Card {

    private String type, number, company;
    private double balance;

    public Card(String type, String number, String company, double balance) {
        this.type = type;
        this.number = number;
        this.company = company;
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public String getCompany() {
        return company;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
