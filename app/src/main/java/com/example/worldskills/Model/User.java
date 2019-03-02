package com.example.worldskills.Model;

public class User {
    private String firstName, middleName, lastName, login;
    private Card[] cards;
    private Account[] accounts;
    private Credit[] credits;
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

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
