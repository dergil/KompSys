package com.calculator.api;


import com.calculator.domain.dto.CalculateResponse;
import com.calculator.service.CalculatorService;
import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorApi {

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping("/calculate")
    public CalculateResponse calculate(
            @RequestParam(required = true)  double price,
            @RequestParam(required = true)  double salesTax
            ){
        CalculateRequest request = new CalculateRequest();
        request.setPrice(price);
        request.setSalesTax(salesTax);
        return calculatorService.calculate(request);
    }
}
