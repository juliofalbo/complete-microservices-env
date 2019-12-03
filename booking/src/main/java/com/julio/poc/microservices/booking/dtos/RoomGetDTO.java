package com.julio.poc.microservices.booking.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomGetDTO {

    private UUID id;

    private String description;

    private String name;

    private BigDecimal perNightValue;

    private LocalDateTime creationDate;

    private LocalDateTime lastUpdate;

}
