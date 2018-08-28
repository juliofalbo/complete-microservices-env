package com.julio.poc.microservices.netflixzuulapigatewayserver.clients;

import com.julio.poc.microservices.netflixzuulapigatewayserver.dtos.ApplicationDTO;
import com.julio.poc.microservices.netflixzuulapigatewayserver.dtos.TokenDTO;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

//Use without Ribbon
//@FeignClient(name = "auth", url = "localhost:8400")

@FeignClient(name = "auth")
@RibbonClient(name = "auth")
public interface AuthClient {

    @PostMapping("/auth/claims")
    ApplicationDTO getApplicationName(TokenDTO application);

}
