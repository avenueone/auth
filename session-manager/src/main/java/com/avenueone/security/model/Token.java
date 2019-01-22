package com.avenueone.security.model;

public class Token {
    private Long currentTs;

    private Long expiryTs;

    private String username;

    public Long getCurrentTs() {
        return currentTs;
    }

    public void setCurrentTs(Long currentTs) {
        this.currentTs = currentTs;
    }

    public Long getExpiryTs() {
        return expiryTs;
    }

    public void setExpiryTs(Long expiryTs) {
        this.expiryTs = expiryTs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Token withCurrentTs(Long currentTs) {
        this.currentTs = currentTs;
        return this;
    }

    public Token withExpiryTs(Long expiryTs) {
        this.expiryTs = expiryTs;
        return this;

    }

    public Token withUsername(String username) {
        this.username = username;
        return this;
    }
}
