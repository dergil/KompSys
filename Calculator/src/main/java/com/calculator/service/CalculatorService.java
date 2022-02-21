package com.calculator.service;


import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public CalculateResponse calculate(CalculateRequest request){
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
