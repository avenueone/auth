package com.avenueone.security.config;


import com.avenueone.security.api.AuthController;
import com.avenueone.security.service.UserTokenService;
import com.avenueone.security.util.Encryption;
import com.avenueone.security.web.ServiceRequestFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "application.security")
public class SessionManagementConfig {

    @Value("${application.security.encryption.salt}")
    private String encryptionSalt;

    @Value("${application.security.encryption.key}")
    private String encryptionKey;

    @Value("${application.security.enableCookieAuth}")
    private boolean enableCookieAuth;


    @Value("${application.security.session.timeout}")
    private Long timeout;

    @Value("${application.security.session.cookie.token}")
    private String tokenCookie;

    @Value("${application.security.session.cookie.user}")
    private String userCookie;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private List<String> whitelist;

    public List<String> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }

    @Bean
    public Encryption encryption() {
        Encryption encryption = new Encryption(encryptionKey,encryptionSalt);
        return encryption;
    }

    @Bean
    public ServiceRequestFilter serviceRequestFilter() {
        ServiceRequestFilter filter = new ServiceRequestFilter();
        filter.setTokenCookie(tokenCookie);
        filter.setUserCookie(userCookie);
        filter.setWhitelist(whitelist);
        filter.setContextPath(contextPath);
        filter.setEnableCookieAuth(enableCookieAuth);
        filter.setUserTokenService(userTokenService());
        return filter;
    }

    @Bean
    public UserTokenService userTokenService() {
        UserTokenService userTokenService = new UserTokenService();
        userTokenService.setTimeout(timeout);
        return userTokenService;
    }


    @Bean
    public AuthController authController(){
        AuthController authController = new AuthController();
        authController.setTokenCookie(tokenCookie);
        authController.setUserCookie(userCookie);
        return authController;
    }
}
