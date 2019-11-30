package com.julio.poc.microservices.searching.specifications.domain;

import com.julio.poc.microservices.searching.specifications.enums.SpecificationOperationEnum;

public class SpecificationFilter {

    private String field;

    private FieldIn fieldIn;

    private SpecificationOperationEnum operation;

    private Object value;

    public SpecificationFilter(String field, Object value, SpecificationOperationEnum operation) {
        this.field = field;
        this.value = value;
        this.operation = operation;
    }

    public SpecificationFilter(FieldIn fieldIn, Object value, SpecificationOperationEnum operation) {
        this.fieldIn = fieldIn;
        this.value = value;
        this.operation = operation;
    }

    public String getField() {
        return field;
    }

    public SpecificationOperationEnum getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }

    public FieldIn getFieldIn() {
        return fieldIn;
    }

}