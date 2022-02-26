package kbe.gateway.api;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CalculatorApi {
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;
//    public static final String ROUTING_KEY = "calculate";
    @Value("${calculate_routingkey:calculate}")
    private String ROUTING_KEY;

    public CalculatorApi(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    @GetMapping("/calculate")
    public CalculateResponse calculate(
            @RequestParam double pricePreTax, @RequestParam double salesTax){
        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setPricePreTax(pricePreTax);
        calculateRequest.setSalesTax(salesTax);
        return getCalculateResponse(calculateRequest);
    }

    @Nullable
    private CalculateResponse getCalculateResponse(CalculateRequest calculateRequest) {
        log.info("Sending " + calculateRequest);
        CalculateResponse response = (CalculateResponse) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                ROUTING_KEY,
                calculateRequest);
        log.info("Received " + response);
        return response;
    }
}
