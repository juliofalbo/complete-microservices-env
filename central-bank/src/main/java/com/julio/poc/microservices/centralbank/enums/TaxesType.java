package com.julio.poc.microservices.centralbank.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public enum TaxesType {
    FINANCING("financing"),
    LOAN("loan");

    private String type;
}
