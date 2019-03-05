package com.example.worldskills.Model;

public class Card {

    private String cardNumber, cardName;
    private int type;
    private long balance;
    private boolean isBlocked;

    public Card(String cardNumber, String cardName, int type, long balance, boolean isBlocked) {
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.type = type;
        this.balance = balance;
        this.isBlocked = isBlocked;
    }

    public String getNumber() {
        return cardNumber;
    }

    public String getName() {
        return cardName;
    }

    public int getType() {
        return type;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setName(String cardName) {
        this.cardName = cardName;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
