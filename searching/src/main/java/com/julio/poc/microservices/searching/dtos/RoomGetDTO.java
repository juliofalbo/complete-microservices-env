package com.julio.poc.microservices.searching.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomGetDTO {

    private UUID id;

    private String description;

    private BigDecimal perNightValue;

    private LocalDateTime creationDate;

    private LocalDateTime lastUpdate;

}
