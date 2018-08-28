package com.julio.poc.microservices.falbobank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisRequest {

    @NotEmpty
    private String document;

    @NotEmpty
    private String operation;

    @NotNull
    private BigDecimal requestedValue;

    @Min(1)
    private Integer plots = 1;

}
