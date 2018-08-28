package com.julio.poc.microservices.currencyexchangeservice.services;

import com.julio.poc.microservices.currencyexchangeservice.annotations.PopulatePortOnObjectResponse;
import com.julio.poc.microservices.currencyexchangeservice.entities.ExchangeValue;
import com.julio.poc.microservices.currencyexchangeservice.exceptions.CurrencyExchangeFoundException;
import com.julio.poc.microservices.currencyexchangeservice.repositories.ExchangeValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyExchangeService {

    @Autowired
    private ExchangeValueRepository repository;

    @PopulatePortOnObjectResponse(fieldName = "currencyExchangeMicroservicePort")
    public List<ExchangeValue> findAll() {
        return repository.findAll();
    }

    @PopulatePortOnObjectResponse(fieldName = "currencyExchangeMicroservicePort")
    public ExchangeValue save(ExchangeValue user) {
        return repository.save(user);
    }

    @PopulatePortOnObjectResponse(fieldName = "currencyExchangeMicroservicePort")
    public ExchangeValue findByFromAndTo(String from, String to) {
        return repository.findByFromAndTo(from, to).orElseThrow(() -> new CurrencyExchangeFoundException(String.format("No exchange value found for from %s and to %s", from, to)));
    }

    @PopulatePortOnObjectResponse(fieldName = "currencyExchangeMicroservicePort")
    public ExchangeValue findOne(Long id) {
        return repository.findById(id).orElseThrow(() -> new CurrencyExchangeFoundException(String.format("No exchange value found for id: %s", id)));
    }

    public void deleteById(Long id) {
        repository.delete(findOne(id));
    }


}
