package com.avenueone.security.model;

public class AuthResponse {
    private boolean authentication = false;

    private String fullName;

    private String email;

    public AuthResponse fullName(String name) {
        this.setFullName(name);
        return this;
    }
    public AuthResponse email(String email) {
        this.setEmail(email);
        return this;
    }
    public AuthResponse authenticaton(boolean authentication) {
        this.setAuthentication(authentication);
        return this;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
