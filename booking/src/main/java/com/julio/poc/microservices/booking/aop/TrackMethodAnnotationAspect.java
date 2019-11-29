package com.julio.poc.microservices.booking.aop;

import com.julio.poc.microservices.booking.annotations.TrackMethod;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Configuration
@Slf4j
public class TrackMethodAnnotationAspect {

    private final Environment environment;

    public TrackMethodAnnotationAspect(Environment environment) {
        this.environment = environment;
    }

    @Around("com.julio.poc.microservices.booking.aop.CommonJoinPointConfig.trackMethod()")
    public Object populateUser(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch watch = new StopWatch();
        Integer port = Integer.valueOf(Objects.requireNonNull(environment.getProperty("local.server.port")));
        log.info("Entering in the method {}, in class {}, running on port {}",
                joinPoint.getSignature().getName(),
                joinPoint.getSignature().getDeclaringTypeName(),
                port);

        watch.start();
        Object returnObject = joinPoint.proceed();
        watch.stop();


        log.info("Method {} in class {} took {} milliseconds to execute.",
                joinPoint.getSignature().getName(),
                joinPoint.getSignature().getDeclaringTypeName(),
                watch.getTotalTimeMillis());

        return returnObject;
    }

}
