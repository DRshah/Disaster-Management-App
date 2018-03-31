package com.example.disastermanagement.Files;

public class Register_Token {

    private String token,email;

    public Register_Token() {
    }

    public Register_Token(String token) {
        this.token = token;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}