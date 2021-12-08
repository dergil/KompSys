package com.kbe.kompsys.domain.dto;

import com.kbe.kompsys.domain.model.Car;
import lombok.Data;

@Data
public class CarTaxResponse {
    private double salesTax;
    private double taxAmount;
    private String countryCode;
    private String region;
}
