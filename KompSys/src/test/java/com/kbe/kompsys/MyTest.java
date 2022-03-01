//package com.kbe.kompsys;
//
//import com.kbe.kompsys.amqp.Receiver;
//import com.kbe.kompsys.service.CarServiceImpl;
//import com.kbe.kompsys.util.CarMapper;
//import org.junit.jupiter.api.Test;
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
//import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
//import javax.persistence.Access;
//
//@SpringBootTest
//@SpringJUnitConfig
//public class MyTest {
//    @Autowired
//    private CarServiceImpl carService;
//
//    @Autowired
//    private DirectExchange directExchange;
//
//    @Autowired
//    private RabbitListenerTestHarness harness;
//
//    @Autowired
//    private CarMapper carMapper;
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Test
//    void hro() {
//
//    }
//}
