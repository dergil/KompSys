package com.kbe.kompsys.service.calculator;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import com.kbe.kompsys.service.RabbitMqTransferService;
import com.kbe.kompsys.service.interfaces.CarCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CalculatorServiceImpl implements CarCalculatorService {
    @Autowired
    private RabbitMqTransferService transferService;
    @Value("${tax_queue_routing_key:calculate}")
    private String calculate_queue_routing_key;

    @Override
    public CalculateResponse queryCalculator(CalculateRequest request) {
        log.info("Pre Received CalculateResponse");
        CalculateResponse response = (CalculateResponse) transferService.transferRequest(request, calculate_queue_routing_key);
        log.info("Received " + response);
        return response;
    }
}
