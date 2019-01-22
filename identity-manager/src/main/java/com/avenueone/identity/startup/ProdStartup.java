package com.avenueone.identity.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

@Profile("prod")
@Controller
@Configuration
public class ProdStartup extends BaseStartup {
    private static final Logger log = LoggerFactory.getLogger(ProdStartup.class);

}
