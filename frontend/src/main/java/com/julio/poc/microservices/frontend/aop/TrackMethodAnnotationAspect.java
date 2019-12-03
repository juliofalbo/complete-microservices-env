package com.julio.poc.microservices.frontend.aop;

import java.util.Objects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StopWatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Configuration
@Slf4j
@RequiredArgsConstructor
public class TrackMethodAnnotationAspect {

    private final Environment environment;

    @Around("com.julio.poc.microservices.frontend.aop.CommonJoinPointConfig.trackMethod()")
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
