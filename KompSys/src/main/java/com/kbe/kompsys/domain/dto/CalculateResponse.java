package com.kbe.kompsys.domain.dto;

import lombok.Data;

@Data
public class CalculateResponse {
    double price;
    double salesTax;
    double taxAmount;
}
