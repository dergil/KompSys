package com.github.dergil.kompsys.dto.car.tax;

import com.github.dergil.kompsys.dto.calculate.CalculateView;
import com.github.dergil.kompsys.dto.car.CarView;
import com.github.dergil.kompsys.dto.tax.TaxView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarTaxCalculateView implements Serializable {
    private CarView carView;
    private TaxView taxView;
    private CalculateView calculateView;
}
