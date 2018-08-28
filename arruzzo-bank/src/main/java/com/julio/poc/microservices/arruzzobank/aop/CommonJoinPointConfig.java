package com.julio.poc.microservices.arruzzobank.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
	
	@Pointcut("@annotation(com.julio.poc.microservices.arruzzobank.annotations.PopulatePortOnObjectResponse)")
	public void populatePortAnnotation(){}


}
