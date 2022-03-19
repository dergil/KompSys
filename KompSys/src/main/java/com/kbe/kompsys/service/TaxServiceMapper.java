package com.kbe.kompsys.service;

import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import com.github.dergil.kompsys.dto.car.tax.CarTaxCalculateView;
import com.github.dergil.kompsys.dto.tax.TaxView;
import com.kbe.kompsys.domain.mapper.CalculateViewMapper;
import com.kbe.kompsys.domain.mapper.CarViewMapper;
import com.kbe.kompsys.domain.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaxServiceMapper {
    @Autowired
    private CarViewMapper carViewMapper;
    @Autowired
    private CalculateViewMapper calculateViewMapper;

    public CarTaxCalculateView mapToCarTaxCalculateView(Car car, CalculateResponse calculateResponse, TaxView tax) {
        CarTaxCalculateView response = new CarTaxCalculateView();
        response.setCarView(carViewMapper.toCarView(car));
        response.setCalculateView(calculateViewMapper.toCalculateView(calculateResponse));
        response.setTaxView(tax);
        return response;
    }

}
