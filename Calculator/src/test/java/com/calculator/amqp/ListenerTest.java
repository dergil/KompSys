package com.calculator.amqp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.concurrent.TimeUnit;

import com.calculator.service.CalculatorService;
import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.junit.RabbitAvailable;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness.InvocationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@RabbitAvailable(queues = "#{queue.name}")
@SpringBootTest
@SpringJUnitConfig
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ListenerTest {
//    https://github.com/spring-projects/spring-amqp/blob/a3aedc67072bdfc01850dec7f87195cd8bf86381/spring-rabbit-test/src/test/java/org/springframework/amqp/rabbit/test/examples/ExampleRabbitListenerSpyAndCaptureTest.java

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private RabbitListenerTestHarness harness;

    @MockBean
    public CalculatorService calculatorService;

    @Test
    void receivesRequest() throws InterruptedException {
        CalculateRequest calculateRequest = new CalculateRequest(0.19, 20000);
        this.rabbitTemplate.convertSendAndReceive(this.queue.getName(), calculateRequest);
        Listener listener = this.harness.getSpy("calculate");
        assertThat(listener).isNotNull();
        verify(listener).calculate(calculateRequest);

        InvocationData invocationData = this.harness.getNextInvocationDataFor("calculate", 10, TimeUnit.SECONDS);
        assertThat(invocationData).isNotNull();
        assertThat((CalculateRequest) invocationData.getArguments()[0]).isEqualTo(calculateRequest);
    }

    @Test
    void callsCalculatorServiceCorrectly() {
        CalculateRequest calculateRequest = new CalculateRequest(0.19, 30000);
        this.rabbitTemplate.convertSendAndReceive(this.queue.getName(), calculateRequest);
        when(calculatorService.calculate(calculateRequest)).thenReturn(new CalculateResponse());
        verify(calculatorService).calculate(calculateRequest);
    }

    @Test
    void sendsResponseCorrectly() {
        CalculateRequest calculateRequest = new CalculateRequest(0.19, 40000);
        CalculateResponse mockedResponse = new CalculateResponse();
        mockedResponse.setTaxAmount(1.0);
        when(calculatorService.calculate(calculateRequest)).thenReturn(mockedResponse);
        CalculateResponse receivedResponse = (CalculateResponse) this.rabbitTemplate.convertSendAndReceive(this.queue.getName(), calculateRequest);
        Assertions.assertEquals(mockedResponse, receivedResponse);
    }
}
