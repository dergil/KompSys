package kbe.gateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import kbe.gateway.config.ClientConfiguration;
import kbe.gateway.service.CalculatorService;
import org.junit.jupiter.api.Test;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.junit.RabbitAvailable;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@RabbitAvailable(queues = "calculate")
@DirtiesContext
@SpringRabbitTest
public class GeneralTest {
//    https://github.com/spring-projects/spring-amqp/blob/main/spring-rabbit/src/test/java/org/springframework/amqp/rabbit/core/RabbitTemplateTests.java
    @Autowired
    protected RabbitTemplate template;

//  DirectExchange of Config subclass overrides DirectExchange of ClientConfiguration class, unfortunately
    @Autowired
    public DirectExchange directExchange;

    private final String ROUTE = "calculate";

    @Test
    void hro() {
        CalculatorService calculatorService = new CalculatorService(template, directExchange);
        CalculateRequest calculateRequest = createCalculateRequest();
        calculatorService.getCalculateResponse(createCalculateRequest());
        CalculateRequest out = (CalculateRequest) this.template.receiveAndConvert(ROUTE, 10);
        System.out.println(out.getPricePreTax());
        assertThat(out.getPricePreTax()).isEqualTo(calculateRequest.getPricePreTax());
        assertThat(this.template.receive(ROUTE)).isNull();
    }

    private CalculateRequest createCalculateRequest() {
        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setSalesTax(0.19);
        calculateRequest.setPricePreTax(10000);
        return calculateRequest;
    }

    @Configuration
    @EnableRabbit
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
