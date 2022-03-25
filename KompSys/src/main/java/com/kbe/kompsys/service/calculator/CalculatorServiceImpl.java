package com.kbe.kompsys.service.calculator;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import com.kbe.kompsys.service.RabbitMqTransferService;
import com.kbe.kompsys.service.interfaces.CarCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CalculatorServiceImpl implements CarCalculatorService {
    @Autowired
    private RabbitMqTransferService transferService;
    public final String ROUTING_KEY = "calculate";

    @Override
    public CalculateResponse queryCalculator(CalculateRequest request) {
        CalculateResponse response = (CalculateResponse) transferService.transferRequest(request, ROUTING_KEY);
        log.info("Received " + response);
        return response;
    }
}
