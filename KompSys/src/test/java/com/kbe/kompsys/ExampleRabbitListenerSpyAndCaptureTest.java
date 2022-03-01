package com.kbe.kompsys;

import com.github.dergil.kompsys.dto.car.CarView;
import com.github.dergil.kompsys.dto.car.ReadCarRequest;
import com.kbe.kompsys.amqp.Receiver;
import com.kbe.kompsys.repository.CarRepository;
import com.kbe.kompsys.util.CarMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.junit.RabbitAvailable;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness.InvocationData;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringJUnitConfig
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RabbitAvailable
public class ExampleRabbitListenerSpyAndCaptureTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue carQueue;

    @Autowired
    private CarRepository carRepository;

//    @Autowired
//    private Queue queue2;

    @Autowired
    private DirectExchange directExchange;

    @Autowired
    private RabbitListenerTestHarness harness;

    @Autowired
    private CarMapper carMapper;

    @Test
    public void successfulGet() {
        long id = 3;
        ReadCarRequest readCarRequest = new ReadCarRequest(id);
        CarView response = (CarView) this.rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                "car",
                readCarRequest);
        Assertions.assertEquals(id, carMapper.toCar(response).getId());
    }

    @Test
    void unsuccessfulGet() throws InterruptedException {
        ReadCarRequest readCarRequest = new ReadCarRequest(130);
        this.rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                "car",
                readCarRequest);

        InvocationData invocationData = this.harness.getNextInvocationDataFor("foo", 10, TimeUnit.SECONDS);
        assertThat(invocationData).isNotNull();
        assertThat(invocationData.getThrowable().toString()).contains("NotFoundException");
    }


//    @Configuration
//    @RabbitListenerTest(capture = true)
//    public static class Config2 {
//
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
//    }
}
