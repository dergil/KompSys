package kbe.gateway.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.RemoteInvocationAwareMessageConverterAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ClientConfiguration {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("kompsys");
    }

    @Bean
    public MessageConverter remoteInvocationAwareMessageConverterAdapter() {
        return new RemoteInvocationAwareMessageConverterAdapter();
    }
}

