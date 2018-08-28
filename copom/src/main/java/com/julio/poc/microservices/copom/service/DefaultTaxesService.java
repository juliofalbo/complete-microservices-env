package com.julio.poc.microservices.copom.service;

import com.julio.poc.microservices.copom.annotations.PopulatePortOnObjectResponse;
import com.julio.poc.microservices.copom.properties.PropertiesConfiguration;
import com.julio.poc.microservices.copom.dto.DefaultTaxes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultTaxesService {

    @Autowired
    private PropertiesConfiguration configuration;

    @PopulatePortOnObjectResponse(fieldName = "copomMicroservicePort")
    public DefaultTaxes getDefaultTaxes(){
        return new DefaultTaxes(configuration.getTaxes());
    }

}
