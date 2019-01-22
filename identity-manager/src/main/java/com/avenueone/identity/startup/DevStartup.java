package com.avenueone.identity.startup;

import com.avenueone.identity.model.AvoUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Random;

@Profile("local")
@Controller
@Configuration
public class DevStartup extends BaseStartup {
    private static final Logger log = LoggerFactory.getLogger(DevStartup.class);

    private static Random random = new Random();
    @PostConstruct
    public void devStartup() {
        log.info("Running base startup tasks...");
        // create base data
        clearDevData();
        createBaseData();
    }
    
    private void clearDevData() {
        log.debug("Clearing all users...");
        userRepository.deleteAll();


    }

    public void createBaseData() {
        AvoUser user = new AvoUser();
        user.setFirstName("Phani");
        user.setLastName("Nandiraju");
        user.setUsername("phani");
        user.setPassword("phani");
        userRepository.saveAndFlush(user);

        AvoUser user1 = new AvoUser();
        user1.setFirstName("Raghu");
        user1.setLastName("Madiraju");
        user1.setUsername("raghu");
        user1.setPassword("raghu");
        userRepository.saveAndFlush(user1);

        AvoUser admin = new AvoUser();
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setUsername("admin");
        admin.setPassword("admin");

        userRepository.saveAndFlush(admin);
        log.info("Test data info\n=====================\nUsers: {}\n",userRepository.count());

    }
}
