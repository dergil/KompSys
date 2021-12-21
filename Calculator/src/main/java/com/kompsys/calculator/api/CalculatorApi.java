package com.kompsys.calculator.api;


import com.kompsys.calculator.domain.dto.CalculateRequest;
import com.kompsys.calculator.domain.dto.CalculateResponse;
import com.kompsys.calculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorApi {

    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorApi(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/calculate")
    public CalculateResponse calculate(
            @RequestParam(required = true) double price,
            @RequestParam(required = true) double salesTax
    ) {
        CalculateRequest request = new CalculateRequest();
        request.setPrice(price);
        request.setSalesTax(salesTax);
        return calculatorService.calculate(request);
    }
}
