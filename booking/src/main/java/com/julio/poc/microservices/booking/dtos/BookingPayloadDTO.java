package com.julio.poc.microservices.booking.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingPayloadDTO implements Serializable {

    private UUID idBooking;
    private BigDecimal totalValue;

    private String creditCardNumberEncrypted;
    private String expireDateEncrypted;
    private String ccvEncrypted;
}
