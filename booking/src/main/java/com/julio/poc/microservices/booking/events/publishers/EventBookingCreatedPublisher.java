package com.julio.poc.microservices.booking.events.publishers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.julio.poc.microservices.booking.dtos.BookingPayloadDTO;
import com.tradeshift.amqp.rabbit.handlers.RabbitTemplateHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventBookingCreatedPublisher {

    @Value("${spring.rabbitmq.custom.booking-created.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.custom.booking-created.queueRoutingKey}")
    private String routingKey;

    private final RabbitTemplateHandler rabbitTemplateHandler;

    public void sendEvent(@NonNull final BookingPayloadDTO payload){
        rabbitTemplateHandler.getRabbitTemplate("booking-created")
                .convertAndSend(exchange, routingKey, payload);
    }
}
