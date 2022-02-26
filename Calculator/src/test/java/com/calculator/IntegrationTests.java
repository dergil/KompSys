package com.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

//// first two replace @SpringJunitConfig
//@ContextConfiguration
//@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTests {

    private final RabbitTemplate rabbitTemplate;

    private RabbitAdmin rabbitAdmin;

    public IntegrationTests() {
        rabbitTemplate = new RabbitTemplate();
        rabbitAdmin = new RabbitAdmin(rabbitTemplate);
    }

    @BeforeEach
    void setUp() {
        rabbitAdmin.purgeQueue("calculate");
    }

    @Test
    void test() {

    }

    @Configuration
    public static class Config {
        @Bean
        public DirectExchange directExchange() {
            return new DirectExchange("kompsys");
        }

        @Bean
        public Queue queue() {
            return new Queue("calculate");
        }

        @Bean
        public Binding binding(DirectExchange directExchange, Queue queue) {
            return BindingBuilder.bind(queue)
                    .to(directExchange)
                    .with("calculate");
        }
    }
}
