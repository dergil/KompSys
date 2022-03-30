package com.github.dergil.kompsys.dto.calculate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculateRequest implements Serializable {
    @Min(0)
    double pricePreTax;
    @Min(0)
    double salesTax;
}
