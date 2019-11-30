package com.julio.poc.microservices.searching.dtos;

import java.time.LocalDate;
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
public class BookingGetDTO {

    private UUID idRoom;

    private LocalDate startDate;

    private LocalDate endDate;

    private String guestEmail;

    private LocalDateTime creationDate;

    private LocalDateTime lastUpdate;


}
