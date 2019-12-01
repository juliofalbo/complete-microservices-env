package com.julio.poc.microservices.financial.resources;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.julio.poc.microservices.financial.dtos.TransactionDTO;
import com.julio.poc.microservices.financial.entities.Transaction;
import com.julio.poc.microservices.financial.service.TransactionService;
import com.julio.poc.microservices.financial.specifications.builder.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionResource {

    private final TransactionService service;

    @GetMapping
    public Page<TransactionDTO> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                        @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                        @RequestParam(value = "idBooking", required = false) String idBooking,
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                    @RequestParam(value = "creationDate", required = false) LocalDateTime startDate,
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                    @RequestParam(value = "lastUpdate", required = false) LocalDateTime endDate,
                                        @RequestParam(value = "totalValue", required = false) BigDecimal totalValue
    ) {

        Specification<Transaction> objectSpecification = SpecificationBuilder.init()
                .withEqualFilter("idBooking", idBooking)
                .withEqualFilter("creationDate", startDate)
                .withEqualFilter("lastUpdate", endDate)
                .withEqualFilter("totalValue", totalValue)
                .buildSpec();

        return service.search(PageRequest.of(page, size), objectSpecification);
    }

    @GetMapping("/{id}")
    public TransactionDTO findById(@PathVariable("id") UUID id) {
        return service.findByIdFromResource(id);
    }

}
