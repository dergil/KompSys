package com.calculator.amqp;

import com.calculator.service.CalculatorService;
import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class Listener {

    @Autowired
    CalculatorService calculatorService;

    @RabbitListener(id = "calculate", queues = "#{queue.name}")
    public CalculateResponse calculate(CalculateRequest calculateRequest) {
        log.info("Received " + calculateRequest.toString());
        return calculatorService.calculate(calculateRequest);
    }
}