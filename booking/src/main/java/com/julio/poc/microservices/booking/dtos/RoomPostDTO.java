package com.julio.poc.microservices.booking.dtos;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomPostDTO {

    @Size(message = "The name should have at least 5 characters", min = 5)
    private String name;

    @Size(message = "The description should have at least 5 characters", min = 5)
    private String description;

    @NotNull(message = "Value per night is required")
    private BigDecimal perNightValue;

}
