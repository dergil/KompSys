package com.calculator.service;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalculatorServiceTest {
    @Autowired
    private CalculatorService service;

    @Test
    void calculatorServiceCalculatesCorrectly() {
        CalculateRequest calculateRequest = new CalculateRequest(0.19, 50000);
        CalculateResponse receivedResponse = service.calculate(calculateRequest);
        CalculateResponse correctResponse = calculate(calculateRequest);
        Assertions.assertEquals(receivedResponse, correctResponse);

    }

    private CalculateResponse calculate(CalculateRequest request){
        double price = request.getPricePreTax();
        double salesTax = request.getSalesTax();
        double taxAmount = price * salesTax;
        return assembleCalculateResponse(price, salesTax, taxAmount);
    }

    private CalculateResponse assembleCalculateResponse(double price, double salesTax, double taxAmount) {
        CalculateResponse calculateResponse = new CalculateResponse();
        calculateResponse.setPricePostTax(price + taxAmount);
        calculateResponse.setPricePreTax(price);
        calculateResponse.setSalesTax(salesTax);
        calculateResponse.setTaxAmount(taxAmount);
        return calculateResponse;
    }
}
