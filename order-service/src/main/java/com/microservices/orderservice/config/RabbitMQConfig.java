package com.microservices.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${order.queue.name}")
    private String queueName;
    @Value("${order.queue.exchange}")
    private String exchangeName;
    @Value("${order.queue.routing.key}")
    private String routingKey;

    //Spring bean for rabbitmq queue
    @Bean
    public Queue queue(){
        return new Queue(queueName);
    }
    //Spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchangeName);
    }
    //Spring bean for rabbitmq binding of exchange with queue using routing key
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    //Message converter json <-> pojo

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
    //Configure RabbitTeamplate
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
