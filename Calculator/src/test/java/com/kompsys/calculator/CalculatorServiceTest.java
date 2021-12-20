package com.kompsys.calculator;

import com.kompsys.calculator.domain.dto.CalculateRequest;
import com.kompsys.calculator.domain.dto.CalculateResponse;
import com.kompsys.calculator.service.CalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.AssertTrue;

public class CalculatorServiceTest {

    @Test
    void shouldReturnCorrectCalculation() {
        double price = 90000;
        double salesTax = 0.19;
        CalculatorService service = new CalculatorService();
        CalculateRequest request = new CalculateRequest(price, salesTax);
        CalculateResponse response = service.calculate(request);
        Assertions.assertEquals(response.getPriceTotal(), price + price * salesTax);
        Assertions.assertEquals(response.getTaxAmount(), price * salesTax);
        Assertions.assertEquals(response.getPrice(), price);
        Assertions.assertEquals(response.getSalesTax(), salesTax);
    }
}
