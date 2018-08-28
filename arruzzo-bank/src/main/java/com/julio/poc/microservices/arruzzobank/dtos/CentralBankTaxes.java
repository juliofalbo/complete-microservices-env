package com.julio.poc.microservices.arruzzobank.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CentralBankTaxes {

    private BigDecimal value;

    private String operation;

    private int centralBankMicroservicePort;

    private int copomMicroservicePort;

}
