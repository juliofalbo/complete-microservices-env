package com.julio.poc.microservices.centralbank.clients;

import com.julio.poc.microservices.centralbank.dto.DefaultTaxesCentralBank;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//Use without Ribbon
//@FeignClient(name = "copom", url = "localhost:8000")

@FeignClient(name = "copom")
@RibbonClient(name = "copom")
public interface CopomClient {

    @GetMapping("/default-taxes")
    DefaultTaxesCentralBank getDefaultTaxesByCentralBank();

}
