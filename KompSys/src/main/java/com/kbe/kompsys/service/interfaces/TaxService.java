package com.kbe.kompsys.service.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.dergil.kompsys.dto.car.CarTaxCalculateView;
import com.github.dergil.kompsys.dto.car.CarTaxRequest;

public interface TaxService {
    public CarTaxCalculateView queryCarTaxView(CarTaxRequest request) throws JsonProcessingException;
}
