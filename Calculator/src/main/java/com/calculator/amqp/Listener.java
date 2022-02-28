package com.calculator.amqp;

import com.calculator.service.CalculatorService;
import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public class Listener {

    @Autowired
    CalculatorService calculatorService;

    private boolean failed;

    @RabbitListener(id = "foo", queues = "queue1")
    public CalculateResponse foo(CalculateRequest foo) {
        return calculatorService.calculate(foo);
    }

//    @RabbitListener(id = "bar", queues = "queue2")
//    public void foo(@Payload String foo, @SuppressWarnings("unused") @Header("amqp_receivedRoutingKey") String rk) {
//        if (!failed && foo.equals("ex")) {
//            failed = true;
//            throw new IllegalArgumentException(foo);
//        }
//        failed = false;
//    }

//    public CalculateResponse calculate(CalculateRequest request){
//        double price = request.getPricePreTax();
//        double salesTax = request.getSalesTax();
//        double taxAmount = price * salesTax;
//        return assembleCalculateResponse(price, salesTax, taxAmount);
//    }
//
//    private CalculateResponse assembleCalculateResponse(double price, double salesTax, double taxAmount) {
//        CalculateResponse calculateResponse = new CalculateResponse();
//        calculateResponse.setPricePostTax(price + taxAmount);
//        calculateResponse.setPricePreTax(price);
//        calculateResponse.setSalesTax(salesTax);
//        calculateResponse.setTaxAmount(taxAmount);
//        return calculateResponse;
//    }

}