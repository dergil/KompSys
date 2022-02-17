package com.calculator.service;


import com.calculator.domain.dto.CalculateResponse;
import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public CalculateResponse calculate(CalculateRequest request){
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
