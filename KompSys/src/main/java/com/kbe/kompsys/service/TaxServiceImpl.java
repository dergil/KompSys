package com.kbe.kompsys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import com.github.dergil.kompsys.dto.car.tax.CarTaxCalculateView;
import com.github.dergil.kompsys.dto.car.tax.CarTaxRequest;
import com.github.dergil.kompsys.dto.geolocation.GeolocationResponse;
import com.kbe.kompsys.domain.mapper.CalculateViewMapper;
import com.kbe.kompsys.domain.mapper.CarViewMapper;
import com.kbe.kompsys.domain.mapper.TaxViewMapper;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.domain.model.Tax;
import com.kbe.kompsys.repository.CarRepository;
import com.kbe.kompsys.repository.TaxRepository;
import com.kbe.kompsys.service.interfaces.TaxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

@Service
@Slf4j
public class TaxServiceImpl implements TaxService {
    // Mapper
    @Autowired
    private TaxServiceMapper taxServiceMapper;

    // Repos
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private TaxRepository taxRepository;

    // Services
    @Autowired
    private GeolocationService geolocationService;
    @Autowired
    private CarCalculatorService carCalculatorService;

    @Override
    public CarTaxCalculateView queryCarTaxView(CarTaxRequest request) throws JsonProcessingException, UnknownHostException {
        GeolocationResponse geolocationResponse = queryGeolocation(request.getIpAddress());
        Car car = carRepository.getById(request.getId());
        Tax tax = determineTax(geolocationResponse);
        CalculateResponse calculateResponse = queryCalculator(car, tax);
        CarTaxCalculateView response = taxServiceMapper.mapToCarTaxCalculateView(car, calculateResponse, tax);
        log.info("Returning: " + response);
        return response;
    }

    private Tax determineTax(GeolocationResponse geolocation) {
        return taxRepository.getTaxById(geolocation.getCountryCode());
    }

    private GeolocationResponse queryGeolocation(String ipAdress) throws JsonProcessingException, UnknownHostException {
        return geolocationService.getGeolocation(ipAdress);
    }

    private CalculateResponse queryCalculator (Car car, Tax tax) {
        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setPricePreTax(car.getPrice());
        calculateRequest.setSalesTax(tax.getTax());
        return carCalculatorService.queryCalculator(calculateRequest);
    }
}
