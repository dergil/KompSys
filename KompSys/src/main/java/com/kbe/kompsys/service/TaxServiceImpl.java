package com.kbe.kompsys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import com.github.dergil.kompsys.dto.car.CarTaxCalculateView;
import com.github.dergil.kompsys.dto.car.CarTaxRequest;
import com.github.dergil.kompsys.dto.geolocation.GeolocationResponse;
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
    @Autowired
    private CarViewMapper carViewMapper;
    @Autowired
    private TaxViewMapper taxViewMapper;
    @Autowired
    private CalculateViewMapper calculateViewMapper;

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
    public CarTaxCalculateView queryCarTaxView(CarTaxRequest request) throws JsonProcessingException {
        GeolocationResponse geolocationResponse = queryGeolocation(request.getIpAddress());

        Car car = carRepository.getById(request.getId());
        Tax tax = findTax(geolocationResponse);

        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setPricePreTax(car.getPrice());


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
