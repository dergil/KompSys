package kbe.gateway;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import kbe.gateway.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

//// first two replace @SpringJunitConfig
//@ContextConfiguration
//@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringRabbitTest
public class IntegrationTests {
    @Autowired
    private CalculatorService calculatorService;
    private final RabbitAdmin rabbitAdmin;



    public IntegrationTests() {
        rabbitAdmin = new RabbitAdmin(Config.rabbitTemplate);
    }

    @BeforeEach
    void setUp() {
        rabbitAdmin.purgeQueue("calculate");
    }

    @Test
    void test() {
        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setSalesTax(0.19);
        calculateRequest.setPricePreTax(10000);
        calculatorService.getCalculateResponse(calculateRequest);

        await().atMost(30, TimeUnit.SECONDS)
                .until(isMessagePublishedInQueue());
    }

    private Callable<Boolean> isMessagePublishedInQueue() {
        AMQP.Queue.DeclareOk declareOk = rabbitAdmin.getRabbitTemplate().execute(new ChannelCallback<AMQP.Queue.DeclareOk>() {
            public AMQP.Queue.DeclareOk doInRabbit(Channel channel) throws Exception {
                return channel.queueDeclarePassive("calculate");
            }
        });
        int messageCount = declareOk.getMessageCount();
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return messageCount == 1;
            }
        };
    }

    @Configuration
    public class Config {
        public RabbitTemplate rabbitTemplate;
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
