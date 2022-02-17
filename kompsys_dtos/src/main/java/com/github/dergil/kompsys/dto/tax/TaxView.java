package com.github.dergil.kompsys.dto.tax;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaxView implements Serializable {

    private String countryCodeID;

    private double tax;
}
