package com.kbe.kompsys.service.calculator;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import com.kbe.kompsys.service.interfaces.CarCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CalculatorServiceImpl implements CarCalculatorService {
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;
    public static final String ROUTING_KEY = "calculate";

    public CalculatorServiceImpl(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    @Override
    public CalculateResponse queryCalculator(CalculateRequest request) {
        log.info("Sending " + request.toString());
        CalculateResponse response = (CalculateResponse) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                ROUTING_KEY,
                request);
        log.info("Received " + response);
        return response;
    }
}
