package com.kbe.kompsys.domain.dto.calculate;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CalculateResponse {
    @NotNull
    Double price;
    @NotNull
    Double salesTax;
    @NotNull
    Double taxAmount;
}
