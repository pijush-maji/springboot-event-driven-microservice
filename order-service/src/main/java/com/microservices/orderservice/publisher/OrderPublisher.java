package com.microservices.orderservice.publisher;

import com.microservices.orderservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderPublisher {


    @Value("${order.queue.exchange}")
    private String exchangeName;
    @Value("${order.queue.routing.key}")
    private String routingKey;

    private static final Logger LOG = LoggerFactory.getLogger(OrderPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public OrderPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void orderPublisher(OrderEvent orderEvent){
        LOG.info(String.format("Order event sent to rabbitmq -> %s",orderEvent.toString()));
        rabbitTemplate.convertAndSend(exchangeName,routingKey,orderEvent);
    }
}
