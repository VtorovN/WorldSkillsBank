package com.example.worldskills;

public class User {
    private String firstName, middleName, lastName, login;
    private Card[] cards;
    private Account[] accounts;
    private Credit[] credits;

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public Card[] getCards() {
        return cards;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public Credit[] getCredits() {
        return credits;
    }
}
