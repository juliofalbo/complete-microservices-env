package com.julio.poc.microservices.financial.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
	
	@Pointcut("@annotation(com.julio.poc.microservices.financial.annotations.TrackMethod)")
	public void trackMethod(){}

}
