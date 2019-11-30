package com.julio.poc.microservices.searching.specifications.domain;

public class FieldIn {

    private String fieldList;

    private String attributeName;

    public FieldIn(final String fieldList, final String attributeName) {
        this.fieldList = fieldList;
        this.attributeName = attributeName;
    }

    public String getFieldList() {
        return fieldList;
    }

    public String getAttributeName() {
        return attributeName;
    }
}