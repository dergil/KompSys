package com.kbe.kompsys.domain.dto.car;

import com.kbe.kompsys.domain.dto.calculate.CalculateView;
import com.kbe.kompsys.domain.dto.tax.TaxView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarTaxCalculateView {
    private CarView carView;
    private TaxView taxView;
    private CalculateView calculateView;
}
