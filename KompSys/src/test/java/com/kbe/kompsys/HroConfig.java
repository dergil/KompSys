//package com.kbe.kompsys;
//
//import com.kbe.kompsys.amqp.Receiver;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.test.RabbitListenerTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//
//@TestConfiguration
//@RabbitListenerTest(capture = true)
//public class HroConfig {
//        @Bean
//        public Receiver receiver() {
//            return new Receiver();
//        }
//
//        @Bean
//        public ConnectionFactory connectionFactory() {
//            return new CachingConnectionFactory("localhost");
//        }
//
//        @Bean
//        public DirectExchange directExchange() {
//            return new DirectExchange("kompsys");
//        }
//
//        @Bean
//        public Queue carQueue() {
//            return new Queue("car", false);
//        }
//
//        @Bean
//        public Binding carBinding(DirectExchange directExchange,
//                                  Queue carQueue) {
//            return BindingBuilder.bind(carQueue)
//                    .to(directExchange)
//                    .with("car");
//        }
//
//        @Bean
//        public RabbitAdmin admin(ConnectionFactory cf) {
//            return new RabbitAdmin(cf);
//        }
//
//        @Bean
//        public RabbitTemplate template(ConnectionFactory cf) {
//            return new RabbitTemplate(cf);
//        }
//
//        @Bean
//        public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory cf) {
//            SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
//            containerFactory.setConnectionFactory(cf);
//            return containerFactory;
//        }
//}
