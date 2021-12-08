package com.kbe.kompsys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbe.kompsys.domain.dto.*;
import com.kbe.kompsys.domain.mapper.CarEditMapper;
import com.kbe.kompsys.domain.mapper.CarTaxResponseMapper;
import com.kbe.kompsys.domain.mapper.CarViewMapper;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.domain.model.Tax;
import com.kbe.kompsys.repository.CarRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.Format;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarEditMapper carEditMapper;
    @Autowired
    private CarViewMapper carViewMapper;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarTaxResponseMapper carTaxResponseMapper;
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

    public CarTaxView queryCarTaxView(CarTaxRequest request) throws JsonProcessingException {


        GeolocationResponse geolocationResponse = queryGeolocation(request.getIpAddress());
        TaxResponse taxResponse = queryTaxRate(geolocationResponse);

        Optional<Car> car = carRepository.findById(request.getId());
        if (car.isEmpty())
            return null;

        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setPrice(car.get().getPrice());
        calculateRequest.setSalesTax(taxResponse.getTax().getTax()); //unschoen

        CalculateResponse calculateResponse = carCalculatorService.queryCalculator(calculateRequest);

        CarTaxResponse response = new CarTaxResponse();
        /// BUild RESPONSE
        //        Car foundCar = car.get();
        //        CarTaxResponse carTaxResponse = carTaxResponseMapper.create(foundCar);
//        carTaxResponse.setSalesTax(calculateResponse.getSalesTax());
//        carTaxResponse.setTaxAmount(calculateResponse.getTaxAmount());
//        carTaxResponse.setCountryCode(geolocationResponse.getCountryCode());
//        carTaxResponse.setRegion(geolocationResponse.getRegion());
        return null; // todo Mapping toView klaeren
    }

    private Tax queryTax(GeolocationResponse geolocation) {
        //search entitys for region/country
        return null;
    }

    private GeolocationResponse queryGeolocation(String ipAdress) throws JsonProcessingException {
        return geolocationService.getGeolocation(ipAdress);
    }

    private TaxResponse queryTaxRate(GeolocationResponse geolocation) throws JsonProcessingException {

        TaxResponse tr = new TaxResponse();
        tr.setCountry(geolocation.getCountryCode());
        tr.setRegion(geolocation.getRegion());
        tr.setTax(queryTax(geolocation));//get Tax from repo
        return tr;
    }
}
