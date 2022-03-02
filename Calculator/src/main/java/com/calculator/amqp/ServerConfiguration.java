package com.calculator.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ServerConfiguration {

    @Value("${calculate_routingkey:calculate}")
    private String ROUTING_KEY;

    @Value("${main_service_exchange_name:kompsys}")
    private String main_service_exchange_name;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(main_service_exchange_name);
    }

    @Bean
    public Queue queue() {
        return new Queue("calculate");
    }

    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue) {
        return BindingBuilder.bind(queue)
                .to(directExchange)
                .with(ROUTING_KEY);
    }
}

