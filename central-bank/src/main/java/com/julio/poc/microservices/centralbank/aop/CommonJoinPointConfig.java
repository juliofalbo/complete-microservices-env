package com.julio.poc.microservices.centralbank.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
	
	@Pointcut("@annotation(com.julio.poc.microservices.centralbank.annotations.PopulatePortOnObjectResponse)")
	public void populatePortAnnotation(){}


}
