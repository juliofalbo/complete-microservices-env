package com.julio.poc.microservices.financial.events.publishers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.julio.poc.microservices.financial.dtos.PaymentAnalyzedDTO;
import com.tradeshift.amqp.rabbit.handlers.RabbitTemplateHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventPaymentAnalyzedPublisher {

    @Value("${spring.rabbitmq.custom.payment-analyzed.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.custom.payment-analyzed.queueRoutingKey}")
    private String routingKey;

    private final RabbitTemplateHandler rabbitTemplateHandler;

    public void sendEvent(@NonNull final PaymentAnalyzedDTO payload){
        rabbitTemplateHandler.getRabbitTemplate("payment-analyzed")
                .convertAndSend(exchange, routingKey, payload);
    }
}
