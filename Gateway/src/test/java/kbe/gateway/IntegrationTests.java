package kbe.gateway;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.junit.RabbitAvailable;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@SpringRabbitTest
@RabbitAvailable(queues = "calculate")
public class IntegrationTests {
  private final String ROUTE = "calculate";
  @Autowired
  protected RabbitTemplate template;

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
