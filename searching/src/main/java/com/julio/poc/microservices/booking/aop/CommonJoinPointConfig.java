package com.julio.poc.microservices.booking.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
	
	@Pointcut("@annotation(com.julio.poc.microservices.booking.annotations.PopulatePortOnObjectResponse)")
	public void populatePortAnnotation(){}


}
