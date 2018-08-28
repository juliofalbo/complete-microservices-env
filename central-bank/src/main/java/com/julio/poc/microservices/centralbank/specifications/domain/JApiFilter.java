package com.julio.poc.microservices.centralbank.specifications.domain;

import com.julio.poc.microservices.centralbank.specifications.enums.JApiOperationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JApiFilter {
    private String field;
    private JApiOperationEnum operation;
    private Object value;

    public JApiFilter(String field) {
        this.field = field;
    }

}