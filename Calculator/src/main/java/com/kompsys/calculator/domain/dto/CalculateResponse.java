package com.kompsys.calculator.domain.dto;

import lombok.Data;

@Data
public class CalculateResponse {
    double priceTotal;
    double price;
    double salesTax;
    double taxAmount;
}
