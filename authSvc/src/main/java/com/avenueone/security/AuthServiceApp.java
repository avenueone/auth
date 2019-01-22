package com.avenueone.security;

import com.avenueone.security.api.AuthController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

@SpringBootApplication(scanBasePackages = {"com.avenueone.security", "com.avenueone.identity"})
@EnableAutoConfiguration
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.avenueone.security", "com.avenueone.identity"} )
@EnableJpaRepositories(basePackages = {"com.avenueone.security", "com.avenueone.identity"})
@EnableZuulProxy
public class AuthServiceApp extends AsyncConfigurerSupport {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApp.class, args);
    }

}
