package com.julio.poc.microservices.financial.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {

    public TransactionDTO(UUID idBooking, String analysis, BigDecimal totalValue) {
        this.idBooking = idBooking;
        this.analysis = analysis;
        this.totalValue = totalValue;
    }

    private UUID id;

    private UUID idBooking;

    private String analysis;

    private BigDecimal totalValue;

    private LocalDateTime creationDate;

    private LocalDateTime lastUpdate;

}
