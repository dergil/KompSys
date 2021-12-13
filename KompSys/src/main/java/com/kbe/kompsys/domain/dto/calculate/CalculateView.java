package com.kbe.kompsys.domain.dto.calculate;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CalculateView {
    @NotNull
    Double price;
    @NotNull
    Double salesTax;
    @NotNull
    Double taxAmount;
}
