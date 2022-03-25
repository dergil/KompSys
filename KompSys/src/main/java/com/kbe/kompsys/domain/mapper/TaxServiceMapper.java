package com.kbe.kompsys.domain.mapper;

import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import com.github.dergil.kompsys.dto.car.tax.CarTaxCalculateView;
import com.github.dergil.kompsys.dto.tax.TaxView;
import com.kbe.kompsys.domain.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class
TaxServiceMapper {
    @Autowired
    protected CarViewMapper carViewMapper;
    @Autowired
    protected CalculateViewMapper calculateViewMapper;

    @Mapping(target = "carView", expression = "java(carViewMapper.toCarView(car))")
    @Mapping(target = "taxView", source = "taxView")
    @Mapping(target = "calculateView", expression = "java(calculateViewMapper.toCalculateView(calculateResponse))")
    public abstract CarTaxCalculateView toCarTaxCalculateView(Car car, CalculateResponse calculateResponse, TaxView taxView);
}
