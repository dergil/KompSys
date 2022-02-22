package com.kbe.kompsys.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQServerConfig {
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("kompsys");
    }

    @Bean
    public Queue carQueue() {
        return new Queue("car", false);
    }

    @Bean
    public Binding carBinding(DirectExchange directExchange,
                              Queue carQueue) {
        return BindingBuilder.bind(carQueue)
                .to(directExchange)
                .with("car");
    }
}
