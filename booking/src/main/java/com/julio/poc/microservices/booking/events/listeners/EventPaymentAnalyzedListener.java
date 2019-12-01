package com.julio.poc.microservices.booking.events.listeners;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julio.poc.microservices.booking.annotations.TrackMethod;
import com.julio.poc.microservices.financial.dtos.PaymentAnalyzedDTO;
import com.julio.poc.microservices.booking.service.BookingService;
import com.tradeshift.amqp.annotation.EnableRabbitRetryAndDlq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventPaymentAnalyzedListener {

    private final BookingService service;

    @TrackMethod
    @RabbitListener(containerFactory = "payment-analyzed", queues = "${spring.rabbitmq.custom.payment-analyzed.queue}")
    @EnableRabbitRetryAndDlq(event = "payment-analyzed", directToDlqWhen = Exception.class)
    public void onMessage(Message message) throws IOException {
        PaymentAnalyzedDTO bookingPayloadDTO = new ObjectMapper().readValue(message.getBody(), PaymentAnalyzedDTO.class);
        log.info("Message received on queue [{}] with payload {}", "queue.payment.analyzed", bookingPayloadDTO);
        service.addBookingState(bookingPayloadDTO);
    }

}