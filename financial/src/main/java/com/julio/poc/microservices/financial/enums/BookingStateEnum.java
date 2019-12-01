package com.julio.poc.microservices.financial.enums;

import java.util.Random;

public enum BookingStateEnum {
    PAYMENT_PENDING,
    PAYMENT_APPROVED,
    PAYMENT_REFUSED;

    public static BookingStateEnum getRandomAnalysis() {
        return new Random().nextBoolean() ? PAYMENT_APPROVED : PAYMENT_REFUSED;
    }
}
