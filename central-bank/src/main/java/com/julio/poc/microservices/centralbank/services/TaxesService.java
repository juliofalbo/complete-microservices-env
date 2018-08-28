package com.julio.poc.microservices.centralbank.services;

import com.julio.poc.microservices.centralbank.annotations.PopulatePortOnObjectResponse;
import com.julio.poc.microservices.centralbank.clients.CopomClient;
import com.julio.poc.microservices.centralbank.dto.DefaultTaxesCentralBank;
import com.julio.poc.microservices.centralbank.dto.Taxes;
import com.julio.poc.microservices.centralbank.entities.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TaxesService {

    @Autowired
    private CopomClient copomClient;

    @Autowired
    private OperationService operationTypeService;

    @PopulatePortOnObjectResponse(fieldName = "centralBankMicroservicePort")
    public Taxes searchTaxes(String operation) {
        Operation result = operationTypeService.findByOperation(operation);
        DefaultTaxesCentralBank defaultTaxesByCentralBank = copomClient.getDefaultTaxesByCentralBank();
        return new Taxes(defaultTaxesByCentralBank.getTaxes().multiply(result.getMultiply()), result.getOperation(), defaultTaxesByCentralBank.getCopomMicroservicePort());
    }

    @PopulatePortOnObjectResponse(fieldName = "centralBankMicroservicePort")
    public Taxes fallback(String operation) {
        Operation result = operationTypeService.findByOperation(operation);
        return new Taxes(result.getMultiply(), result.getOperation(), 0);
    }

}
