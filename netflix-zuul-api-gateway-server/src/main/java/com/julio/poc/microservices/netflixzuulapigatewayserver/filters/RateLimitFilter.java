package com.julio.poc.microservices.netflixzuulapigatewayserver.filters;

import com.julio.poc.microservices.netflixzuulapigatewayserver.clients.AuthClient;
import com.julio.poc.microservices.netflixzuulapigatewayserver.dtos.TokenDTO;
import com.julio.poc.microservices.netflixzuulapigatewayserver.enums.ZuulFilterType;
import com.julio.poc.microservices.netflixzuulapigatewayserver.exceptions.RateLimitException;
import com.julio.poc.microservices.netflixzuulapigatewayserver.exceptions.UnauthorizeException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;

@Slf4j
@Component
public class RateLimitFilter extends ZuulFilter {

    @Autowired
    private AuthClient authClient;

    @Value("${zuul.rate-limit}")
    private Long rateLimit;

    private HashMap<String, Long> rateLimitInMemoryControl = new HashMap<>();

    @Override
    public String filterType() {
        return ZuulFilterType.PRE.getValue();
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        String token = currentContext.getRequest().getHeader("X-AUTH-TOKEN");

        if (StringUtils.isEmpty(token)) {
            throw new UnauthorizeException("Invalid token");
        }

        if (!calculateAndValidateRate(authClient.getApplicationName(new TokenDTO(token)).getAppName())) {
            throw new RateLimitException("request limit reached");
        }

        return null;
    }

    private Boolean calculateAndValidateRate(@NonNull String appName) {
        Long currentRate = rateLimitInMemoryControl.get(appName);
        if(currentRate == null){
            currentRate = 0L;
        }
        log.info("Application: {}, Current Rate: {}, Rate Limit: {}", appName, currentRate, rateLimit);
        if (currentRate <= rateLimit) {
            rateLimitInMemoryControl.put(appName, currentRate + 1);
            return true;
        }
        return false;
    }
}
