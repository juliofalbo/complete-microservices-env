package com.julio.poc.microservices.falbobank.aop;

import com.julio.poc.microservices.falbobank.annotations.PopulatePortOnObjectResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Aspect
@Configuration
@Slf4j
public class PortAspect {

    @Autowired
    private Environment environment;

    @Around("com.julio.poc.microservices.falbobank.aop.CommonJoinPointConfig.populatePortAnnotation()")
    public Object populateUser(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entering in the method {} in the class {}", joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName());

        Method method = getMethod(joinPoint);
        PopulatePortOnObjectResponse methodAnnotations = method.getAnnotation(PopulatePortOnObjectResponse.class);

        Object returnObject = joinPoint.proceed();

        if(returnObject instanceof Collection<?>){
            return ((Collection) returnObject).stream().map(object -> popularFieldPort(methodAnnotations, object)).collect(Collectors.toList());
        }

        return popularFieldPort(methodAnnotations, returnObject);
    }

    private Object popularFieldPort(PopulatePortOnObjectResponse methodAnnotations, Object returnObject) {
        Arrays.asList(returnObject.getClass().getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            try {
                if(field.getName().equals(methodAnnotations.fieldName())){
                    field.set(returnObject, Integer.valueOf(environment.getProperty("local.server.port")));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return returnObject;
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
