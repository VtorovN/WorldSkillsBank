package com.example.worldskills;

public class Token{
    private String token;
    private static String currentToken;

    public String getToken() { return token; }

    public void deleteToken() { token = null; }

    public static String getCurrentToken() { return currentToken; }

    public static void deleteCurrentToken() { currentToken = null; }

    public static void setCurrentToken(String token) { currentToken = token; }
}
