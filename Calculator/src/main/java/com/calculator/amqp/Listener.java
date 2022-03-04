package com.calculator.amqp;

import com.calculator.service.CalculatorService;
import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class Listener {

    @Autowired
    CalculatorService calculatorService;

    @RabbitListener(id = "foo", queues = "calculate")
    public CalculateResponse foo(CalculateRequest foo) {
        return calculatorService.calculate(foo);
    }
}