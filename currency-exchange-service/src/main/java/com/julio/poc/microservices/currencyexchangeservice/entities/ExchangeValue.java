package com.julio.poc.microservices.currencyexchangeservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ExchangeValue {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "currency_from")
    private String from;

    @Column(name = "currency_to")
    private String to;
    private BigDecimal conversionMultiple;

    private transient int currencyExchangeMicroservicePort;

}
