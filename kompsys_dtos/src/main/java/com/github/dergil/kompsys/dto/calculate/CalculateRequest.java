package com.github.dergil.kompsys.dto.calculate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculateRequest implements Serializable {
    double pricePreTax;
    double salesTax;
}
