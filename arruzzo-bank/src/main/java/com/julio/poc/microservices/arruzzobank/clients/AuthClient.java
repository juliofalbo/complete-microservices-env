package com.julio.poc.microservices.arruzzobank.clients;

import com.julio.poc.microservices.arruzzobank.dtos.ApplicationDTO;
import com.julio.poc.microservices.arruzzobank.dtos.TokenDTO;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

//Use without Ribbon
//@FeignClient(name = "central-bank", url = "localhost:8000")

@FeignClient(name = "auth")
@RibbonClient(name = "auth")
public interface AuthClient {

    @PostMapping("/auth")
    TokenDTO getToken(ApplicationDTO application);

}
