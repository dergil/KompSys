//package com.calculator.amqp;
//
//import com.calculator.service.CalculatorService;
//import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
//import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.test.RabbitListenerTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
////@Component
//public class Receiver {
//
//    @Autowired
//    public CalculatorService calculatorService;
//
//    @RabbitListener(id="foo", queues = "calculate")
//    public CalculateResponse receive(CalculateRequest request) {
//        return calculatorService.calculate(request);
//    }
//}
