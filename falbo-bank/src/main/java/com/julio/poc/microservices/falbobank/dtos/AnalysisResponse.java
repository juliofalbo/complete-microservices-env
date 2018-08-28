package com.julio.poc.microservices.falbobank.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalysisResponse {

    private String document;
    private String operation;
    private BigDecimal totalValue;
    private BigDecimal taxes;
    private BigDecimal requestedValue;
    private Integer plots;
    private BigDecimal plotValue;

    private int falboBankMicroservicePort;
    private int centralBankMicroservicePort;

}
