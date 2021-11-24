package com.kbe.kompsys.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaxResponse {
    private long id;

    private String name;
    private double price;
    private float milesPerGallon;
    private int cylinders;
    private int displacement;
    private int horsepower;
    private int weightInPounds;
    private float acceleration;
    private Date year;
    private String origin;

    private double salesTax;
    private double taxAmount;

    private String countryCode;
    private String region;
}
