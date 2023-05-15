package com.microservices.orderservice.controller;

import com.microservices.orderservice.dto.Order;
import com.microservices.orderservice.dto.OrderEvent;
import com.microservices.orderservice.publisher.OrderPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderPublisher orderPublisher;

    public OrderController(OrderPublisher orderPublisher) {
        this.orderPublisher = orderPublisher;
    }

    @PostMapping("/orders")
    public String placeOrder(
            @RequestBody Order order){
        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent(
          "Pending",
          "Order is in pending state",
          order
        );

        orderPublisher.orderPublisher(orderEvent);
        return "Order sent to the RabbitMQ...";
    }
}
