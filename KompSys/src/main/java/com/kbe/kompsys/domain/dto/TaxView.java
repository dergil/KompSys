package com.kbe.kompsys.domain.dto;

import lombok.Data;

@Data
public class TaxView {
    private double price;
    private double tax;
    private String countrycode;

}
