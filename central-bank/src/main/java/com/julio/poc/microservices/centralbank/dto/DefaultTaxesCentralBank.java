package com.julio.poc.microservices.centralbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultTaxesCentralBank {

    private BigDecimal taxes;

    private int copomMicroservicePort;

}
