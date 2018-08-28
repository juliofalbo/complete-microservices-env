package com.julio.poc.microservices.copom.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
	
	@Pointcut("@annotation(com.julio.poc.microservices.copom.annotations.PopulatePortOnObjectResponse)")
	public void populatePortAnnotation(){}


}
