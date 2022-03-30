package com.kbe.calculator.amqp;

import com.kbe.calculator.service.CalculatorService;
import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@RabbitListener(id = "calculate_listener", queues = "calculate", returnExceptions = "true")
public class Receiver {

  @Autowired
  CalculatorService calculatorService;

  @RabbitHandler
  public CalculateResponse handleCalculateRequest(CalculateRequest request) {
    log.info("Receiving: " + request.toString());
    return calculatorService.calculate(request);
  }
}
