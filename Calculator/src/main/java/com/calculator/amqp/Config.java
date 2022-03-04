package com.calculator.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RabbitListenerTest(capture = true)
public class Config {

    @Value("${calculate_routingkey:calculate}")
    private String ROUTING_KEY;

    @Value("${main_service_exchange_name:kompsys}")
    private String main_service_exchange_name;

    @Bean
    public Listener listener() {
        return new Listener();
    }

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        return new CachingConnectionFactory("localhost");
//    }

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

//    @Bean
//    public RabbitAdmin admin(ConnectionFactory cf) {
//        return new RabbitAdmin(cf);
//    }
//
//    @Bean
//    public RabbitTemplate template(ConnectionFactory cf) {
//        return new RabbitTemplate(cf);
//    }
//
//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory cf) {
//        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
//        containerFactory.setConnectionFactory(cf);
//        return containerFactory;
//    }

}