package com.example.worldskills.Model;

public class Token{
    private String token;
    private static Token currentToken;

    public String getToken() { return token; }

    public void deleteToken() { token = null; }

    public static Token getCurrentToken() { return currentToken; }

    public static void deleteCurrentToken() { currentToken = null; }

    public static void setCurrentToken(Token token) { currentToken = token; }
}
