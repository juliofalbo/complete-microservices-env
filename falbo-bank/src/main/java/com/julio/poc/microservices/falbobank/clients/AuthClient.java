package com.julio.poc.microservices.falbobank.clients;

import com.julio.poc.microservices.falbobank.dtos.ApplicationDTO;
import com.julio.poc.microservices.falbobank.dtos.TokenDTO;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

//Use without Ribbon
//@FeignClient(name = "central-bank", url = "localhost:8000")

@FeignClient(name = "auth")
@RibbonClient(name = "auth")
public interface AuthClient {

    @PostMapping("/auth")
    TokenDTO getToken(ApplicationDTO application);

}
