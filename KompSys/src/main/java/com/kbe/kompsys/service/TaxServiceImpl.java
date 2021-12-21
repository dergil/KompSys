package com.kbe.kompsys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kbe.kompsys.domain.dto.calculate.CalculateRequest;
import com.kbe.kompsys.domain.dto.calculate.CalculateResponse;
import com.kbe.kompsys.domain.dto.car.CarTaxCalculateView;
import com.kbe.kompsys.domain.dto.car.CarTaxRequest;
import com.kbe.kompsys.domain.dto.geolocation.GeolocationResponse;
import com.kbe.kompsys.domain.mapper.CalculateViewMapper;
import com.kbe.kompsys.domain.mapper.CarViewMapper;
import com.kbe.kompsys.domain.mapper.TaxViewMapper;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.domain.model.Tax;
import com.kbe.kompsys.repository.CarRepository;
import com.kbe.kompsys.repository.TaxRepository;
import com.kbe.kompsys.service.interfaces.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxServiceImpl implements TaxService {
    // Mapper
    private final CarViewMapper carViewMapper;
    private final TaxViewMapper taxViewMapper;
    private final CalculateViewMapper calculateViewMapper;

    // Repos
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private TaxRepository taxRepository;

    // Services
    private final GeolocationService geolocationService;
    private final CarCalculatorService carCalculatorService;

    @Autowired
    public TaxServiceImpl(CarViewMapper carViewMapper, TaxViewMapper taxViewMapper, CalculateViewMapper calculateViewMapper, GeolocationService geolocationService, CarCalculatorService carCalculatorService) {
        this.carViewMapper = carViewMapper;
        this.taxViewMapper = taxViewMapper;
        this.calculateViewMapper = calculateViewMapper;
        this.geolocationService = geolocationService;
        this.carCalculatorService = carCalculatorService;
    }

    @Override
    public CarTaxCalculateView queryCarTaxView(CarTaxRequest request) throws JsonProcessingException {
        GeolocationResponse geolocationResponse = queryGeolocation(request.getIpAddress());

        Car car = carRepository.getById(request.getId());
        Tax tax = findTax(geolocationResponse);

        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setPrice(car.getPrice());


        calculateRequest.setSalesTax(tax.getTax());

        CalculateResponse calculateResponse = carCalculatorService.queryCalculator(calculateRequest);

        CarTaxCalculateView response = new CarTaxCalculateView();
        response.setCarView(carViewMapper.toCarView(car));
        response.setCalculateView(calculateViewMapper.toCalculateView(calculateResponse));
        response.setTaxView(taxViewMapper.toTaxView(tax));

        return response;
    }

    private Tax findTax(GeolocationResponse geolocation) {
        return taxRepository.getTaxById(geolocation.getCountryCode());
    }

    private GeolocationResponse queryGeolocation(String ipAdress) throws JsonProcessingException {
        return geolocationService.getGeolocation(ipAdress);
    }
}
