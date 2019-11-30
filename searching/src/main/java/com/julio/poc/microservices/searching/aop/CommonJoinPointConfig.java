package com.julio.poc.microservices.searching.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {
	
	@Pointcut("@annotation(com.julio.poc.microservices.searching.annotations.TrackMethod)")
	public void trackMethod(){}

}
