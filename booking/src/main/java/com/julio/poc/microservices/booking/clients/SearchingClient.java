package com.julio.poc.microservices.booking.clients;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "auth")
@RibbonClient(name = "auth")
public interface SearchingClient {

}
