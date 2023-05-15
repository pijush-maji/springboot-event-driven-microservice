package com.microservices.stockservice.consumer;

import com.microservices.stockservice.dto.OrderEvent;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = "${order.queue.name}")
    public void consume(OrderEvent orderEvent){
        LOG.info(String.format("Order event received -> %s",orderEvent.toString()));

    }
}
