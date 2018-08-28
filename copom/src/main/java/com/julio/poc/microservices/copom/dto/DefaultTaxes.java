package com.julio.poc.microservices.copom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DefaultTaxes {

    public DefaultTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    private BigDecimal taxes;

    private int copomMicroservicePort;

}
