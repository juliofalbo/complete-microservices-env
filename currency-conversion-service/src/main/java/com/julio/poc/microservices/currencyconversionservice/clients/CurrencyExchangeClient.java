package com.julio.poc.microservices.currencyconversionservice.clients;

import com.julio.poc.microservices.currencyconversionservice.dtos.CurrencyConversion;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Use without Ribbon
//@FeignClient(name = "currency-exchange-service", url = "localhost:8000")

//Use without Zuul
//@FeignClient(name = "currency-exchange-service")

@FeignClient(name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeClient {

    String SERVER_NAME = "/currency-exchange-service";

    //It is necessary to enter the name of the application who is server (currency-exchange-service) because we are intercepting the requests with Zuul.
    @GetMapping(SERVER_NAME + "/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion getExchangeValue(@PathVariable("from") String from,
                                        @PathVariable("to") String to);

}
