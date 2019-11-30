package com.julio.poc.microservices.searching.specifications.enums;

public enum SpecificationOperationEnum {
    EQUAL("="),
    EQUAL_SUBCLASS("="),
    BIGGER(">"),
    SMALLER("<"),
    IN("in"),
    LIKE("%");

    private String value;

    SpecificationOperationEnum(String value) {
        this.value = value;
    }

    public boolean equalsValue(String othervalue) {
        return value.equals(othervalue);
    }

    public String getValue() {
        return this.value;
    }
}