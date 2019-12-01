package com.julio.poc.microservices.financial.events.listeners;

import java.io.IOException;
import java.util.Random;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julio.poc.microservices.financial.annotations.TrackMethod;
import com.julio.poc.microservices.booking.dtos.BookingPayloadDTO;
import com.julio.poc.microservices.financial.dtos.PaymentAnalyzedDTO;
import com.julio.poc.microservices.financial.dtos.TransactionDTO;
import com.julio.poc.microservices.financial.events.publishers.EventPaymentAnalyzedPublisher;
import com.julio.poc.microservices.financial.exception.RetryableException;
import com.julio.poc.microservices.financial.exception.ValidationException;
import com.julio.poc.microservices.financial.service.TransactionService;
import com.tradeshift.amqp.annotation.EnableRabbitRetryAndDlq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventBookingCreatedListener {

    private final TransactionService transactionService;
    private final EventPaymentAnalyzedPublisher eventPaymentAnalyzedPublisher;

    @TrackMethod
    @RabbitListener(containerFactory = "booking-created", queues = "${spring.rabbitmq.custom.booking-created.queue}")
    @EnableRabbitRetryAndDlq(event = "booking-created", retryWhen = RetryableException.class)
    public void onMessage(Message message) throws IOException {
        BookingPayloadDTO bookingPayloadDTO = new ObjectMapper().readValue(message.getBody(), BookingPayloadDTO.class);
        log.info("Message received on queue [{}] with payload {}", "queue.booking.created", bookingPayloadDTO);

        // This is an unblocker retry strategy that is used to Spring RabbitMQ Tuning Library
        // Read more: https://github.com/Tradeshift/spring-rabbitmq-tuning/wiki/Queues-Strategy
        if (new Random().nextBoolean()) {
            throw new RetryableException("Simulating a Retryable Scenario");
        }

        PaymentAnalyzedDTO analyze = transactionService.analyze(bookingPayloadDTO);
        transactionService.save(new TransactionDTO(bookingPayloadDTO.getIdBooking(), analyze.getState().name(), bookingPayloadDTO.getTotalValue()));
        eventPaymentAnalyzedPublisher.sendEvent(analyze);
    }

}