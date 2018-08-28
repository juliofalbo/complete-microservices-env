package com.julio.poc.microservices.currencyexchangeservice.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
	
	@Pointcut("@annotation(com.julio.poc.microservices.currencyexchangeservice.annotations.PopulatePortOnObjectResponse)")
	public void populatePortAnnotation(){}


}
