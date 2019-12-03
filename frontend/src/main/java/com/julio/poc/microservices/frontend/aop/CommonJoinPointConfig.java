package com.julio.poc.microservices.frontend.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
	
	@Pointcut("@annotation(com.julio.poc.microservices.frontend.annotations.TrackMethod)")
	public void trackMethod(){}

}
