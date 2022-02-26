package kbe.gateway.service;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CalculatorService {
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;
    public static final String ROUTING_KEY = "calculate";

    public CalculatorService(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    @Nullable
    public CalculateResponse getCalculateResponse(CalculateRequest calculateRequest) {
        log.info("Sending " + calculateRequest);
        CalculateResponse response = (CalculateResponse) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                ROUTING_KEY,
                calculateRequest);
        log.info("Received " + response);
        return response;
    }
}
