package com.kbe.kompsys.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RabbitListenerTest(capture = true)
public class Config {

    @Value("${main_service_exchange_name:kompsys}")
    private String main_service_exchange_name;

    @Value("${car_queue:car}")
    private String car_queue;

    @Value("${car_queue_routing_key:car}")
    private String car_queue_routing_key;

    @Bean
    public Receiver listener() {
        return new Receiver();
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
