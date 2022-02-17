package com.calculator.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculateResponse {
    double priceTotal;
    double price;
    double salesTax;
    double taxAmount;
}
