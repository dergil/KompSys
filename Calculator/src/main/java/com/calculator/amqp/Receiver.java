package com.calculator.amqp;

import com.calculator.domain.dto.CalculateResponse;
import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

//    public void receiveMessage(CalculateRequest calculateRequest) {
//        System.out.println("Received <" + calculateRequest.toString() + ">");
//    }

    @RabbitListener(queues = "request", concurrency = "3")
    public CalculateResponse receive(CalculateRequest request) {
        double price = request.getPrice();
        double salesTax = request.getSalesTax();
        double taxAmount = price * salesTax;

        CalculateResponse calculateResponse = new CalculateResponse();

        calculateResponse.setPriceTotal(price + taxAmount);
        calculateResponse.setPrice(price);
        calculateResponse.setSalesTax(salesTax);
        calculateResponse.setTaxAmount(taxAmount);
        return calculateResponse;
    }
}
