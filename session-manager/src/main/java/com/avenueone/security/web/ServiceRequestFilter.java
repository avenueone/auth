package com.avenueone.security.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.avenueone.security.service.UserTokenService;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.ZuulFilter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ServiceRequestFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(ServiceRequestFilter.class);

    private String tokenCookie;

    private String userCookie;

    private String contextPath;

    private List<String> whitelist;

    private UserTokenService userTokenService;

    private boolean enableCookieAuth;

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

    public List<String> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public UserTokenService getUserTokenService() {
        return userTokenService;
    }

    public void setUserTokenService(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    public boolean isEnableCookieAuth() {
        return enableCookieAuth;
    }

    public void setEnableCookieAuth(boolean enableCookieAuth) {
        this.enableCookieAuth = enableCookieAuth;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if(isWhitelisted(request.getRequestURI())) {
            return false;
        }
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        validate();
        return null;
    }


    /**
     *
     * @throws Exception
     */
    public void validate() {
        log.info("Validating request..");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        String token = "";
        String username = "";
        if(!isWhitelisted(request.getRequestURI()) && enableCookieAuth) {
            log.info("Validating cookies...");
            try {
                for(Cookie cookie: request.getCookies()) {
                    if(cookie.getName().equals(tokenCookie)) {
                        token = cookie.getValue();
                    } else if(cookie.getName().equals(userCookie)) {
                        username = cookie.getValue();
                    }
                }
                if(!userTokenService.validateToken(token, username)) {
                    ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                    ctx.setResponseBody("{\"status\":\""+HttpStatus.UNAUTHORIZED+"\"}");
                    ctx.setSendZuulResponse(false);
                    ctx.unset();
                } else {
                    response.addCookie(new Cookie(tokenCookie, token));
                    response.addCookie(new Cookie(userCookie, username));
                }
            } catch (Exception e) {
                // Do nothing as the final return is handled below
                e.printStackTrace();
            }
        }
    }

    private boolean isWhitelisted(String path) {
        return whitelist.contains(StringUtils.remove(path, contextPath));
    }

}