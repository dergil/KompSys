package com.github.dergil.kompsys.dto.calculate;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CalculateResponse implements Serializable {

    @NotNull
    Double pricePostTax;
    @NotNull
    Double pricePreTax;
    @NotNull
    Double salesTax;
    @NotNull
    Double taxAmount;
}
