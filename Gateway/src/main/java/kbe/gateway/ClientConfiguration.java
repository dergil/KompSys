package kbe.gateway;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ClientConfiguration {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("kompsys");
    }

    @Bean
//    evtl. anderer import für MessageConverter
    public MessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

