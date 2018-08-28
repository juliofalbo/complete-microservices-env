package com.julio.poc.microservices.centralbank.specifications.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JApiOperationEnum {
    EQUAL("="),
    BIGGER(">"),
    SMALLER("<"),
    LIKE("%");

    private String value;

    public boolean equalsValue(String othervalue) {
        return value.equals(othervalue);
    }
}
