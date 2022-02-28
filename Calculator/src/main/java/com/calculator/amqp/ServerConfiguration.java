//package com.calculator.amqp;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.annotation.EnableRabbit;
//import org.springframework.amqp.rabbit.test.RabbitListenerTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.TestPropertySource;
//
//import javax.sound.midi.Receiver;
//
//@Configuration
//@RabbitListenerTest(capture=true)
//public class ServerConfiguration {
//
//    @Bean
//    Receiver receiver() {
//        return new Receiver();
//    }
//
//    @Bean
//    public DirectExchange directExchange() {
//        return new DirectExchange("kompsys");
//    }
//
//    @Bean
//    public Queue queue() {
//        return new Queue("calculate");
//    }
//
//    @Bean
//    public Binding binding(DirectExchange directExchange, Queue queue) {
//        return BindingBuilder.bind(queue)
//                .to(directExchange)
//                .with("calculate");
//    }
//}
//
