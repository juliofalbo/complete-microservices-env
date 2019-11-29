package com.julio.poc.microservices.booking.dtos;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class BookingIdentityGetDTO{

    private UUID idRoom;
    private LocalDate startDate;
    private LocalDate endDate;

}
