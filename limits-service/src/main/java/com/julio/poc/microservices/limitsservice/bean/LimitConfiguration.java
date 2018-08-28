package com.julio.poc.microservices.limitsservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LimitConfiguration {

    private int min;
    private int max;

}
