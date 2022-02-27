package kbe.gateway;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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

//// first two replace @SpringJunitConfig
//@ContextConfiguration
//@ExtendWith(SpringExtension.class)
//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@SpringRabbitTest
@RabbitAvailable(queues = "calculate")
public class IntegrationTests {
    @Autowired
    protected RabbitTemplate template;
//    @Autowired
//    private RabbitAdmin rabbitAdmin;

    private final String ROUTE = "calculate";
//    @Autowired
//    private CalculatorService calculatorService;


//    @Autowired
//    private final RabbitAdmin rabbitAdmin;

//    @Test
//    public void testReceiveNonBlocking() throws Exception {
//        CalculateRequest calculateRequest = createCalculateRequest();
//        this.template.convertAndSend(ROUTE, calculateRequest);
////        () -> (String) this.template.receiveAndConvert(ROUTE), str -> str != null
//        CalculateRequest out = (CalculateRequest) this.template.receiveAndConvert(ROUTE, 10);
//        System.out.println(out.getPricePreTax());
//        assertThat(out.getPricePreTax()).isEqualTo(calculateRequest.getPricePreTax());
//        assertThat(this.template.receive(ROUTE)).isNull();
//    }

    private CalculateRequest createCalculateRequest() {
        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setSalesTax(0.19);
        calculateRequest.setPricePreTax(10000);
        return calculateRequest;
    }

    @Test
    public void testReceiveBlocking() throws Exception {
        CalculateRequest calculateRequest = createCalculateRequest();
        template.convertSendAndReceive(
                "kompsys",
                ROUTE,
                calculateRequest);
        CalculateRequest out = (CalculateRequest) this.template.receiveAndConvert(ROUTE, 10);
        assertThat(out.getPricePreTax()).isEqualTo(calculateRequest.getPricePreTax());
        assertThat(this.template.receive(ROUTE)).isNull();
    }


//    @Test
//    void test() {
//        CalculateRequest calculateRequest = new CalculateRequest();
//        calculateRequest.setSalesTax(0.19);
//        calculateRequest.setPricePreTax(10000);
////        calculatorService.getCalculateResponse(calculateRequest);
//        getCalculateResponse(calculateRequest);
//
//        await().atMost(10, TimeUnit.SECONDS)
//                .until(isMessagePublishedInQueue());
//    }
//
//    private Callable<Boolean> isMessagePublishedInQueue() {
//        AMQP.Queue.DeclareOk declareOk = rabbitTemplate.execute(new ChannelCallback<AMQP.Queue.DeclareOk>() {
//            public AMQP.Queue.DeclareOk doInRabbit(Channel channel) throws Exception {
//                return channel.queueDeclarePassive("calculate");
//            }
//        });
////        int messageCount = declareOk.getMessageCount();
////        AMQP.Channel channel = rabbitTemplate.execute(new ChannelCallback<>() {
////            public AMQP.Channel doInRabbit(Channel channel) throws Exception {
////                return  channel.;
////            }
////        });
//
//        return new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                return messageCount == 1;
//            }
//        };
//    }

    public CalculateResponse getCalculateResponse(CalculateRequest calculateRequest) {
//        log.info("Sending " + calculateRequest);
        CalculateResponse response = (CalculateResponse) template.convertSendAndReceive(
                "kompsys",
                "calculate",
                "calculate");
//        log.info("Received " + response);
        return response;
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
