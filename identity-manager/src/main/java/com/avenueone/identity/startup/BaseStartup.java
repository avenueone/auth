package com.avenueone.identity.startup;

import com.avenueone.identity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseStartup {

    private static final Logger log = LoggerFactory.getLogger(BaseStartup.class);

    @Autowired
    UserRepository userRepository;

    public BaseStartup() {
        log.info("Running base startup tasks...");
    }


}
