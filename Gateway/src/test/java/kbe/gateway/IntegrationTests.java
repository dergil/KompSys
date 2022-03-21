package kbe.gateway;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import com.rabbitmq.client.AMQP;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.junit.RabbitAvailable;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;


import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@SpringRabbitTest
@RabbitAvailable(queues = "calculate")
public class IntegrationTests {
   @Autowired
   protected RabbitTemplate template;

   private final String ROUTE = "calculate";


   private CalculateRequest createCalculateRequest() {
       CalculateRequest calculateRequest = new CalculateRequest();
       calculateRequest.setSalesTax(0.19);
       calculateRequest.setPricePreTax(10000);
       return calculateRequest;
   }

   @Test
   public void testReceiveBlocking() {
       CalculateRequest calculateRequest = createCalculateRequest();
       template.convertSendAndReceive(
               "kompsys",
               ROUTE,
               calculateRequest);
       CalculateRequest out = (CalculateRequest) this.template.receiveAndConvert(ROUTE, 10);
       assertThat(out.getPricePreTax()).isEqualTo(calculateRequest.getPricePreTax());
       assertThat(this.template.receive(ROUTE)).isNull();
   }
}
