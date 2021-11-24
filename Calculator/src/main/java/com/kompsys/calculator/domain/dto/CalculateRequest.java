package com.kompsys.calculator.domain.dto;

import lombok.Data;

@Data
public class CalculateRequest {
    double price;
    double salesTax;
}
