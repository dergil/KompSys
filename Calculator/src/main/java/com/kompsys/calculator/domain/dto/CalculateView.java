package com.kompsys.calculator.domain.dto;

import lombok.Data;

@Data
public class CalculateView {
    double price;
    double salesTax;
    double taxAmount;
}
