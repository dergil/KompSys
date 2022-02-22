package com.calculator.amqp;

import com.calculator.service.CalculatorService;
import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    public CalculatorService calculatorService;

    @RabbitListener(queues = "calculate")
    public CalculateResponse receive(CalculateRequest request) {
        return calculatorService.calculate(request);
    }
}
