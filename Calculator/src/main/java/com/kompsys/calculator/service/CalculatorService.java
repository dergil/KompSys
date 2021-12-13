package com.kompsys.calculator.service;


import com.kompsys.calculator.domain.dto.CalculateRequest;
import com.kompsys.calculator.domain.dto.CalculateResponse;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public CalculateResponse calculate(CalculateRequest request){
        double price = request.getPrice();
        double salesTax = request.getSalesTax();
        double taxAmount = price * salesTax / 100;

        CalculateResponse calculateResponse = new CalculateResponse();

        calculateResponse.setPriceTotal(price + taxAmount);
        calculateResponse.setPrice(price);
        calculateResponse.setSalesTax(salesTax);
        calculateResponse.setTaxAmount(taxAmount);
        return calculateResponse;
    }
}
