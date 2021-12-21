package com.kbe.kompsys.service.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kbe.kompsys.domain.dto.car.CarTaxCalculateView;
import com.kbe.kompsys.domain.dto.car.CarTaxRequest;

public interface TaxService {
    public CarTaxCalculateView queryCarTaxView(CarTaxRequest request) throws JsonProcessingException;
}
