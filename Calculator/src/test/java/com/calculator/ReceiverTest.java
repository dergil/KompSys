package com.calculator;

import com.calculator.service.CalculatorService;
import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.junit.RabbitAvailable;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.amqp.rabbit.test.mockito.LatchCountDownAndCallRealMethodAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@RabbitAvailable(queues = "calculate")
@SpringBootTest
@SpringJUnitConfig
@DirtiesContext
public class ReceiverTest {
//    @Mock
//    CalculatorService calculatorService;
//    @Autowired
//    DirectExchange directExchange;
    @Autowired
    private RabbitListenerTestHarness harness;

    @Autowired
    private Queue queue;

    @Autowired
    public RabbitTemplate template;
//    @Autowired
//    private RabbitListenerTestHarness harness;
//    @Autowired
//    public Queue queue;
//    @Autowired
//    public Receiver receiver;

    @Test
    void testReceive() throws InterruptedException {
        CalculateRequest calculateRequest = new CalculateRequest(0.19, 10000);
        CalculateResponse response = (CalculateResponse) this.template.convertSendAndReceive(
                "kompsys",
                "calculate",
                calculateRequest);
        System.out.println(response.toString());
        Assertions.assertEquals(response, calculate(calculateRequest));

//        Mockito.verify(calculatorService).calculate(calculateRequest);
    }

//    @Test
//    public void testOneWay() throws Exception {
//        CalculateRequest calculateRequest = new CalculateRequest(0.19, 10000);
//        Receiver listener = this.harness.getSpy("foo");
//        Assertions.assertNotNull(listener);
//
//        LatchCountDownAndCallRealMethodAnswer answer = this.harness.getLatchAnswerFor("foo", 2);
//        doAnswer(answer).when(listener).receive(any(CalculateRequest.class));
//
//        template.convertAndSend(
//                "kompsys",
//                "calculate",
//                calculateRequest);
//
////        this.template.convertAndSend(directExchange.getName(), this.queue.getName(), calculateRequest);
//
////        Assertions.assertTrue(answer.await(15));
//        answer.await(10);
//        verify(listener).receive(calculateRequest);
//}

//    @Configuration
//    @RabbitListenerTest(capture=true)
//    public static class Config {
//
//        @Bean
//        public DirectExchange directExchange() {
//            return new DirectExchange("kompsys");
//        }
//
//
//        @Bean
//        public Queue queue() {
//            return new Queue("calculate");
//        }
//
//        @Bean
//        public Binding binding(DirectExchange directExchange, Queue queue) {
//            return BindingBuilder.bind(queue)
//                    .to(directExchange)
//                    .with("calculate");
//        }
//
//    }

//    @Test
//    public void testTwoWay() throws Exception {
//        CalculateRequest calculateRequest = new CalculateRequest(0.19, 10000);
//        assertEquals(calculate(calculateRequest), this.template.convertSendAndReceive(directExchange.getName(), this.queue.getName(), calculateRequest));
//
//        Receiver listener = this.harness.getSpy("foo");
//        assertNotNull(listener);
//        verify(listener).receive(calculateRequest);
//    }
    public CalculateResponse calculate(CalculateRequest request){
        double price = request.getPricePreTax();
        double salesTax = request.getSalesTax();
        double taxAmount = price * salesTax;
        return assembleCalculateResponse(price, salesTax, taxAmount);
    }

    private CalculateResponse assembleCalculateResponse(double price, double salesTax, double taxAmount) {
        CalculateResponse calculateResponse = new CalculateResponse();
        calculateResponse.setPricePostTax(price + taxAmount);
        calculateResponse.setPricePreTax(price);
        calculateResponse.setSalesTax(salesTax);
        calculateResponse.setTaxAmount(taxAmount);
        return calculateResponse;
    }
}
