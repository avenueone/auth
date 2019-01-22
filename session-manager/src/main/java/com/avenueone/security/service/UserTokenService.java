package com.avenueone.security.service;

import com.avenueone.security.model.Token;
import com.avenueone.security.util.Encryption;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTokenService {

    private Long timeout;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private Encryption encryption;

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String generateUserToken(String username) throws  Exception {
        Long currentTs = System.currentTimeMillis();
        Long expiryTs = currentTs + (timeout*1000);
        return encryption.encrypt(objectMapper.writeValueAsString(new Token()
            .withCurrentTs(currentTs)
            .withExpiryTs(expiryTs)
            .withUsername(username)
        ));
    }

    public boolean validateToken(String encryptedToken, String username) throws  Exception {
        Long currentTs = System.currentTimeMillis();
        Token token = objectMapper.readValue(encryption.decrypt(encryptedToken), Token.class);
        if(username.equals(token.getUsername()) && (currentTs <= token.getExpiryTs())) {
            return true;
        } else {
            return false;
        }
    }

    public Token updateToken(String encryptedToken, String username) throws  Exception {
        Long currentTs = System.currentTimeMillis();
        Token token = objectMapper.readValue(encryption.decrypt(encryptedToken), Token.class);
        token.setUsername(username);
        token.setExpiryTs(System.currentTimeMillis() + timeout);
        return token;
    }
}
