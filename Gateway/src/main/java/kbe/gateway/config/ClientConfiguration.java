package kbe.gateway.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.RemoteInvocationAwareMessageConverterAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ClientConfiguration {

    @Value("${main_service_exchange_name:kompsys}")
    private String main_service_exchange_name;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(main_service_exchange_name);
    }

    @Bean
    public MessageConverter remoteInvocationAwareMessageConverterAdapter() {
        return new RemoteInvocationAwareMessageConverterAdapter();
    }
}

