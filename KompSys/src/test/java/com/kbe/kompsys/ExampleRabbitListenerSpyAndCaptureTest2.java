//package com.kbe.kompsys;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.willAnswer;
//import static org.mockito.Mockito.verify;
//
//import java.util.Collection;
//import java.util.concurrent.TimeUnit;
//
//import com.kbe.kompsys.amqp.Listener;
//import com.kbe.kompsys.service.CarServiceImpl;
//import com.kbe.kompsys.service.interfaces.CarService;
//import org.junit.jupiter.api.Test;
//
//import org.springframework.amqp.core.AnonymousQueue;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.junit.RabbitAvailable;
//import org.springframework.amqp.rabbit.test.RabbitListenerTest;
//import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
//import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness.InvocationData;
//import org.springframework.amqp.rabbit.test.mockito.LatchCountDownAndCallRealMethodAnswer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
///**
// * @author Gary Russell
// * @since 1.6
// *
// */
//@SpringBootTest
//@SpringJUnitConfig
//@DirtiesContext
//@RabbitAvailable
//public class ExampleRabbitListenerSpyAndCaptureTest2 {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    private Queue queue1;
//
//    @Autowired
//    private Queue queue2;
//
//    @Autowired
//    private RabbitListenerTestHarness harness;
//
//    @Autowired
//    private CarServiceImpl carService;
//
//    @Test
//    public void testTwoWay() throws Exception {
//        assertThat(this.rabbitTemplate.convertSendAndReceive(this.queue1.getName(), "foo")).isEqualTo("FOO");
//
//        Listener listener = this.harness.getSpy("foo");
//        assertThat(listener).isNotNull();
//        verify(listener).foo("foo");
//
//        InvocationData invocationData = this.harness.getNextInvocationDataFor("foo", 10, TimeUnit.SECONDS);
//        assertThat(invocationData).isNotNull();
//        assertThat((String) invocationData.getArguments()[0]).isEqualTo("foo");
//        assertThat((String) invocationData.getResult()).isEqualTo("FOO");
//    }
//
//    @Test
//    public void testOneWay() throws Exception {
//        Listener listener = this.harness.getSpy("bar");
//        assertThat(listener).isNotNull();
//
//        LatchCountDownAndCallRealMethodAnswer answer = this.harness.getLatchAnswerFor("bar", 3);
//        willAnswer(answer).given(listener).foo(anyString(), anyString());
//
//        this.rabbitTemplate.convertAndSend(this.queue2.getName(), "bar");
//        this.rabbitTemplate.convertAndSend(this.queue2.getName(), "baz");
//        this.rabbitTemplate.convertAndSend(this.queue2.getName(), "ex");
//
//        assertThat(answer.await(10)).isTrue();
//        verify(listener).foo("bar", this.queue2.getName());
//        verify(listener).foo("baz", this.queue2.getName());
//
//        InvocationData invocationData = this.harness.getNextInvocationDataFor("bar", 10, TimeUnit.SECONDS);
//        assertThat(invocationData).isNotNull();
//        Object[] args = invocationData.getArguments();
//        assertThat((String) args[0]).isEqualTo("bar");
//        assertThat((String) args[1]).isEqualTo(queue2.getName());
//
//        invocationData = this.harness.getNextInvocationDataFor("bar", 10, TimeUnit.SECONDS);
//        assertThat(invocationData).isNotNull();
//        args = invocationData.getArguments();
//        assertThat((String) args[0]).isEqualTo("baz");
//        assertThat((String) args[1]).isEqualTo(queue2.getName());
//
//        invocationData = this.harness.getNextInvocationDataFor("bar", 10, TimeUnit.SECONDS);
//        assertThat(invocationData).isNotNull();
//        args = invocationData.getArguments();
//        assertThat((String) args[0]).isEqualTo("ex");
//        assertThat((String) args[1]).isEqualTo(queue2.getName());
//        assertThat(invocationData.getThrowable()).isNotNull();
//        assertThat(invocationData.getThrowable().getMessage()).isEqualTo("ex");
//
//        invocationData = this.harness.getNextInvocationDataFor("bar", 10, TimeUnit.SECONDS);
//        assertThat(invocationData).isNotNull();
//        args = invocationData.getArguments();
//        assertThat((String) args[0]).isEqualTo("ex");
//        assertThat((String) args[1]).isEqualTo(queue2.getName());
//        assertThat(invocationData.getThrowable()).isNull();
//
//        Collection<Exception> exceptions = answer.getExceptions();
//        assertThat(exceptions).hasSize(1);
//        assertThat(exceptions.iterator().next()).isInstanceOf(IllegalArgumentException.class);
//    }
//
//    @Configuration
//    @RabbitListenerTest(capture = true)
//    public static class Config {
//
//        @Bean
//        public Listener listener() {
//            return new Listener();
//        }
//
//        @Bean
//        public ConnectionFactory connectionFactory() {
//            return new CachingConnectionFactory("localhost");
//        }
//
//        @Bean
//        public Queue queue1() {
//            return new AnonymousQueue();
//        }
//
//        @Bean
//        public Queue queue2() {
//            return new AnonymousQueue();
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
//
//    }
//
//
//
//}
