package com.kbe.kompsys.domain.dto;

import lombok.Data;

@Data
public class TaxRequest {
    private double price;
    private String country;
    private String region;
}
