package com.kompsys.calculator.api;


import com.kompsys.calculator.domain.dto.CalculateRequest;
import com.kompsys.calculator.domain.dto.CalculateView;
import com.kompsys.calculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorApi {

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping("/calculate")
    public CalculateView calculate(
            @RequestParam(required = true)  double price,
            @RequestParam(required = true)  double salesTax
            ){
        CalculateRequest request = new CalculateRequest();
        request.setPrice(price);
        request.setSalesTax(salesTax);
        return calculatorService.calculate(request);
    }
}
