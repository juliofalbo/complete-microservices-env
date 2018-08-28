package com.julio.poc.microservices.centralbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Taxes {

    public Taxes(BigDecimal value, String operation, int copomMicroservicePort) {
        this.value = value;
        this.operation = operation;
        this.copomMicroservicePort = copomMicroservicePort;
    }

    private BigDecimal value;

    private String operation;

    private int centralBankMicroservicePort;

    private int copomMicroservicePort;

}
