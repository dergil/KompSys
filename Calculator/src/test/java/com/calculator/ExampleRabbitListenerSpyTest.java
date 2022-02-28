//package com.calculator;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.verify;
//
//import java.util.concurrent.TimeUnit;
//
//import com.calculator.amqp.Listener;
//import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
//import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import org.springframework.amqp.core.AnonymousQueue;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.junit.BrokerRunning;
//import org.springframework.amqp.rabbit.test.RabbitListenerTest;
//import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
//import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
//import org.springframework.amqp.rabbit.test.mockito.LatchCountDownAndCallRealMethodAnswer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// * @author Gary Russell
// * @since 1.6
// *
// */
//@ContextConfiguration
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringJUnit4ClassRunner.class)
//@DirtiesContext
//public class ExampleRabbitListenerSpyTest {
//
//    @Rule
//    public BrokerRunning brokerRunning = BrokerRunning.isRunning();
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
//    @Test
//    public void testTwoWay() throws Exception {
//        CalculateRequest calculateRequest = new CalculateRequest(0.19, 10000);
//        assertEquals(calculate(calculateRequest), this.rabbitTemplate.convertSendAndReceive(this.queue1.getName(), calculateRequest));
//
//        Listener listener = this.harness.getSpy("foo");
//        assertNotNull(listener);
//        verify(listener).foo(calculateRequest);
//    }
//
//    public CalculateResponse calculate(CalculateRequest request){
//        double price = request.getPricePreTax();
//        double salesTax = request.getSalesTax();
//        double taxAmount = price * salesTax;
//        return assembleCalculateResponse(price, salesTax, taxAmount);
//    }
//
//    private CalculateResponse assembleCalculateResponse(double price, double salesTax, double taxAmount) {
//        CalculateResponse calculateResponse = new CalculateResponse();
//        calculateResponse.setPricePostTax(price + taxAmount);
//        calculateResponse.setPricePreTax(price);
//        calculateResponse.setSalesTax(salesTax);
//        calculateResponse.setTaxAmount(taxAmount);
//        return calculateResponse;
//    }
//
////    @Test
////    public void testOneWay() throws Exception {
////        Listener listener = this.harness.getSpy("bar");
////        assertNotNull(listener);
////
////        LatchCountDownAndCallRealMethodAnswer answer = new LatchCountDownAndCallRealMethodAnswer(2);
////        doAnswer(answer).when(listener).foo(anyString(), anyString());
////
////        this.rabbitTemplate.convertAndSend(this.queue2.getName(), "bar");
////        this.rabbitTemplate.convertAndSend(this.queue2.getName(), "baz");
////
////        assertTrue(answer.getLatch().await(10, TimeUnit.SECONDS));
////        verify(listener).foo("bar", this.queue2.getName());
////        verify(listener).foo("baz", this.queue2.getName());
////    }
//
//
//
////    public static class Listener {
////
////        @RabbitListener(id = "foo", queues = "queue1")
////        public String foo(String foo) {
////            return foo.toUpperCase();
////        }
////
////        @RabbitListener(id = "bar", queues = "queue2")
////        public void foo(@Payload String foo, @Header("amqp_receivedRoutingKey") String rk) {
////        }
////
////    }
//
//}
