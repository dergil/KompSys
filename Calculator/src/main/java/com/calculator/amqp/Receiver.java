package com.calculator.amqp;

import com.calculator.service.CalculatorService;
import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@RabbitListener(id = "foo", queues = "calculate", returnExceptions = "true")
public class Receiver {

  @Autowired
  CalculatorService calculatorService;

  @RabbitHandler
  public CalculateResponse handleCalculateRequest(CalculateRequest request) {
    return calculatorService.calculate(request);
  }
}
