package com.kbe.kompsys.domain.dto;


import com.kbe.kompsys.domain.dto.calculate.CalculateResponse;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.domain.model.Tax;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarTaxCalculateView {
    private Car car;
    private Tax tax;
    private CalculateResponse calculateResponse;
}
