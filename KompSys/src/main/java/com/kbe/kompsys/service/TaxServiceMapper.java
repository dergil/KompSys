package com.kbe.kompsys.service;

import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import com.github.dergil.kompsys.dto.car.tax.CarTaxCalculateView;
import com.kbe.kompsys.domain.mapper.CalculateViewMapper;
import com.kbe.kompsys.domain.mapper.CarViewMapper;
import com.kbe.kompsys.domain.mapper.TaxViewMapper;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.domain.model.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaxServiceMapper {
    @Autowired
    private CarViewMapper carViewMapper;
    @Autowired
    private TaxViewMapper taxViewMapper;
    @Autowired
    private CalculateViewMapper calculateViewMapper;

    public CarTaxCalculateView mapToCarTaxCalculateView(Car car, CalculateResponse calculateResponse, Tax tax){
        CarTaxCalculateView response = new CarTaxCalculateView();
        response.setCarView(carViewMapper.toCarView(car));
        response.setCalculateView(calculateViewMapper.toCalculateView(calculateResponse));
        response.setTaxView(taxViewMapper.toTaxView(tax));
        return response;
    }

}
