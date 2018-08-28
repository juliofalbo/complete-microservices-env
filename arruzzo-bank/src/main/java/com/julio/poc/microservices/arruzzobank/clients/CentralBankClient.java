package com.julio.poc.microservices.arruzzobank.clients;

import com.julio.poc.microservices.arruzzobank.dtos.CentralBankTaxes;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

//Use without Ribbon
//@FeignClient(name = "central-bank", url = "localhost:8000")

//Use without Zuul
//@FeignClient(name = "central-bank")

@FeignClient(name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "central-bank")
public interface CentralBankClient {

    String SERVER_NAME = "/central-bank";

    //It is necessary to enter the name of the application who is server (central-bank) because we are intercepting the requests with Zuul.
    @GetMapping(SERVER_NAME + "/taxes?operation={operation}")
    CentralBankTaxes getCentralBankTaxes(@RequestHeader("X-AUTH-TOKEN") String token, @PathVariable("operation") String operation);

}
