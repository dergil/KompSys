package com.kompsys.calculator.service;


import com.kompsys.calculator.domain.dto.CalculateRequest;
import com.kompsys.calculator.domain.dto.CalculateView;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
    public CalculateView calculate(CalculateRequest request){
        double price = request.getPrice();
        double salesTax = request.getSalesTax();
        double taxAmount = price * salesTax / 100;

        CalculateView calculateView = new CalculateView();

        calculateView.setPrice(price);
        calculateView.setSalesTax(salesTax);
        calculateView.setTaxAmount(taxAmount);
        return calculateView;
    }
}
