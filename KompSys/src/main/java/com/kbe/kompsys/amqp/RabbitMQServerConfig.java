package com.kbe.kompsys.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.RemoteInvocationAwareMessageConverterAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQServerConfig {
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("kompsys");
    }

    @Bean
    public Queue create_queue() {
        return new Queue("create_car", false);
    }

    @Bean
    public Binding create_binding(DirectExchange directExchange,
                           Queue create_queue) {
        return BindingBuilder.bind(create_queue)
                .to(directExchange)
                .with("create_car");
    }

    @Bean
    public Queue update_queue() {
        return new Queue("update_car", false);
    }

    @Bean
    public Binding update_binding(DirectExchange directExchange,
                           Queue update_queue) {
        return BindingBuilder.bind(update_queue)
                .to(directExchange)
                .with("update_car");
    }

    @Bean
    public Queue delete_queue() {
        return new Queue("delete_car", false);
    }

    @Bean
    public Binding delete_binding(DirectExchange directExchange,
                           Queue delete_queue) {
        return BindingBuilder.bind(delete_queue)
                .to(directExchange)
                .with("delete_car");
    }




//    @Bean
//    public MessageConverter remoteInvocationAwareMessageConverterAdapter() {
//        return new RemoteInvocationAwareMessageConverterAdapter();
//    }


}
