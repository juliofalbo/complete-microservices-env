package com.julio.poc.microservices.financial.dtos;

import java.io.Serializable;
import java.util.UUID;

import com.julio.poc.microservices.booking.enums.BookingStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAnalyzedDTO implements Serializable {

    private UUID idBooking;
    private BookingStateEnum state;

}
