package com.julio.poc.microservices.booking.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.julio.poc.microservices.booking.entities.Booking;
import com.tradeshift.amqp.rabbit.handlers.RabbitTemplateHandler;
import lombok.NonNull;

@Component
public class EventBookingCreatedPublisher {

    @Value("${spring.rabbitmq.custom.booking-created.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.custom.booking-created.queueRoutingKey}")
    private String routingKey;

    private final RabbitTemplateHandler rabbitTemplateHandler;

    public EventBookingCreatedPublisher(RabbitTemplateHandler rabbitTemplateHandler) {
        this.rabbitTemplateHandler = rabbitTemplateHandler;
    }

    public void sendEvent(@NonNull final UUID bookingId){
        rabbitTemplateHandler.getRabbitTemplate("booking-created")
                .convertAndSend(exchange, routingKey, bookingId);
    }
}
