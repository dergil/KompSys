package com.kbe.kompsys.service.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.dergil.kompsys.dto.car.tax.CarTaxCalculateView;
import com.github.dergil.kompsys.dto.car.tax.CarTaxRequest;

import java.net.UnknownHostException;

public interface TaxService {
    CarTaxCalculateView queryCarTaxView(CarTaxRequest request) throws JsonProcessingException, UnknownHostException;
}
