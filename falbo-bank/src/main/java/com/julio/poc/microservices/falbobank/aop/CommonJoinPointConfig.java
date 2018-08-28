package com.julio.poc.microservices.falbobank.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
	
	@Pointcut("@annotation(com.julio.poc.microservices.falbobank.annotations.PopulatePortOnObjectResponse)")
	public void populatePortAnnotation(){}


}
