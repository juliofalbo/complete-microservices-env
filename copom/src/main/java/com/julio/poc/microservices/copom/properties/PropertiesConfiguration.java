package com.julio.poc.microservices.copom.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConfigurationProperties("copom")
@Data
public class PropertiesConfiguration {

    private BigDecimal taxes;

}
