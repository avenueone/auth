package com.avenueone.identity.service;

import com.avenueone.identity.model.AvoUser;
import com.avenueone.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public AuthService() {
    }

    @Autowired
    private UserRepository userRepository;

    public AvoUser getUser(String username, String password) {
        return userRepository.findByUsername(username);
    }
}
