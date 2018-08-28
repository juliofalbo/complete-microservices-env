package com.julio.poc.microservices.netflixzuulapigatewayserver.filters;

import com.julio.poc.microservices.netflixzuulapigatewayserver.enums.ZuulFilterType;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class LoggingFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return ZuulFilterType.PRE.getValue();
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        log.info("request uri: {}, request: {}", request, request.getRequestURI());
        return null;
    }
}
