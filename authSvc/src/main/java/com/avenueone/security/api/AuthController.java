package com.avenueone.security.api;

import com.avenueone.identity.model.AvoUser;
import com.avenueone.identity.service.AuthService;
import com.avenueone.security.model.AuthRequest;
import com.avenueone.security.model.AuthResponse;
import com.avenueone.security.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    private UserTokenService userTokenService;

    private String tokenCookie;

    private String userCookie;

    public String getTokenCookie() {
        return tokenCookie;
    }

    public void setTokenCookie(String tokenCookie) {
        this.tokenCookie = tokenCookie;
    }

    public String getUserCookie() {
        return userCookie;
    }

    public void setUserCookie(String userCookie) {
        this.userCookie = userCookie;
    }

    @PostMapping
    public AuthResponse authenticate(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        AuthResponse authResponse = new AuthResponse();
        try{
            AvoUser avoUser = authService.getUser(authRequest.getUsername(), authRequest.getPassword());
            if(avoUser != null){
                String token = userTokenService.generateUserToken(authRequest.getUsername());
                response.addCookie(new Cookie(tokenCookie, token));
                response.addCookie(new Cookie(userCookie, authRequest.getUsername()));
                return authResponse
                        .fullName(avoUser.getFullName())
                        .authenticaton(true);
            } else {
                response.setStatus(401);
                return authResponse;
            }
        } catch (Exception e) {
            response.setStatus(500);
            return authResponse;
        }
    }
}
