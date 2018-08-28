package com.julio.poc.microservices.currencyconversionservice.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
	
	@Pointcut("@annotation(com.julio.poc.microservices.currencyconversionservice.annotations.PopulatePortOnObjectResponse)")
	public void populatePortAnnotation(){}


}
