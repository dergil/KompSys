package com.kbe.kompsys.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQServerConfig {
    @Value("${main_service_exchange_name}")
    private String main_service_exchange_name;

    @Value("${car_queue}")
    private String car_queue;

    @Value("${car_queue_routing_key}")
    private String car_queue_routing_key;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(main_service_exchange_name);
    }

    @Bean
    public Queue carQueue() {
        return new Queue(car_queue, false);
    }

    @Bean
    public Binding carBinding(DirectExchange directExchange,
                              Queue carQueue) {
        return BindingBuilder.bind(carQueue)
                .to(directExchange)
                .with(car_queue_routing_key);
    }
}
