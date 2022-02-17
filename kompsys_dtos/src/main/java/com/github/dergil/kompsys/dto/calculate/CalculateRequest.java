package com.github.dergil.kompsys.dto.calculate;

import lombok.Data;

import java.io.Serializable;

@Data
public class CalculateRequest implements Serializable {
    double price;
    double salesTax;
}
