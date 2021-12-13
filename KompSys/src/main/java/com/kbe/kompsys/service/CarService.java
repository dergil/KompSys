package com.kbe.kompsys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kbe.kompsys.domain.dto.*;
import com.kbe.kompsys.domain.dto.CarTaxCalculateView;
import com.kbe.kompsys.domain.mapper.CarEditMapper;
import com.kbe.kompsys.domain.mapper.CarTaxResponseMapper;
import com.kbe.kompsys.domain.mapper.CarViewMapper;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.domain.model.Tax;
import com.kbe.kompsys.repository.CarRepository;
import com.kbe.kompsys.repository.TaxRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CarService {
    // Mapper
    @Autowired
    private CarEditMapper carEditMapper;
    @Autowired
    private CarViewMapper carViewMapper;
    @Autowired
    private CarTaxResponseMapper carTaxResponseMapper;

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


    @Transactional
    public CarView create(EditCarRequest request) {
        CarEditMapper carEditMapper = Mappers.getMapper(CarEditMapper.class);

        Car car = carEditMapper.create(request);
        carRepository.save(car);
        return carViewMapper.toCarView(car);
    }

    public CarTaxCalculateView queryCarTaxView(CarTaxRequest request) throws JsonProcessingException {


        GeolocationResponse geolocationResponse = queryGeolocation(request.getIpAddress());
        TaxResponse taxResponse = queryTaxRate(geolocationResponse);

        Car car = carRepository.getById(request.getId());

        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setPrice(car.getPrice());

        Tax taxR = taxResponse.getTax();
        calculateRequest.setSalesTax(taxR.getTax());

        CalculateResponse calculateResponse = carCalculatorService.queryCalculator(calculateRequest);

        CarTaxCalculateView response = new CarTaxCalculateView();
        response.setCar(car);
        response.setCalculateResponse(calculateResponse);
        response.setTax(taxR);

        return response; // todo Mapping toView klaeren
    }

    private Tax findTax(GeolocationResponse geolocation) {
        Tax tax = new Tax();
        tax.setTax(12.2);
        tax.setCountryCodeID("1");
        return tax;
        //return taxRepository.getTaxById(geolocation.getCountryCode());
    }

    private GeolocationResponse queryGeolocation(String ipAdress) throws JsonProcessingException {
        return geolocationService.getGeolocation(ipAdress);
    }

    private TaxResponse queryTaxRate(GeolocationResponse geolocation) {

        TaxResponse tr = new TaxResponse();
        tr.setCountry(geolocation.getCountryCode());
        tr.setRegion(geolocation.getRegion());
        tr.setTax(findTax(geolocation));
        return tr;
    }
}
