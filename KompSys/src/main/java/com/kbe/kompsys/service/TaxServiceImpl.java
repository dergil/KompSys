package com.kbe.kompsys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import com.github.dergil.kompsys.dto.car.tax.CarTaxCalculateView;
import com.github.dergil.kompsys.dto.car.tax.CarTaxRequest;
import com.github.dergil.kompsys.dto.geolocation.GeolocationResponse;
import com.github.dergil.kompsys.dto.tax.ReadTaxRequest;
import com.github.dergil.kompsys.dto.tax.TaxView;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.repository.interfaces.CarRepository;
import com.kbe.kompsys.service.interfaces.TaxService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.net.UnknownHostException;

@Service
@Slf4j
public class TaxServiceImpl implements TaxService {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;
    @Value("${tax_queue_routing_key:storage}")
    private String storage_queue_routing_key;

    // Mapper
    @Autowired
    private TaxServiceMapper taxServiceMapper;
    // Repos
    @Autowired
    private CarRepository carRepository;
    // Services
    @Autowired
    private GeolocationService geolocationService;
    @Autowired
    private CarCalculatorService carCalculatorService;

    public TaxServiceImpl(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    @Override
    public CarTaxCalculateView queryCarTaxView(CarTaxRequest request) throws JsonProcessingException, UnknownHostException {
        GeolocationResponse geolocationResponse = queryGeolocation(request.getIpAddress());
        Car car = carRepository.getById(request.getId());
        TaxView tax = determineTax(geolocationResponse);
        CalculateResponse calculateResponse = queryCalculator(car, tax);
        CarTaxCalculateView response = taxServiceMapper.mapToCarTaxCalculateView(car, calculateResponse, tax);
        log.info("Returning: " + response);
        return response;
    }

    private TaxView determineTax(GeolocationResponse geolocation) {
        return (TaxView) sendRequestAndReceiveResponseObject(new ReadTaxRequest(geolocation.getCountryCode()));
//        return taxRepository.getTaxById(geolocation.getCountryCode());
    }

    private GeolocationResponse queryGeolocation(String ipAdress) throws JsonProcessingException, UnknownHostException {
        return geolocationService.getGeolocation(ipAdress);
    }

    private CalculateResponse queryCalculator(Car car, TaxView tax) {
        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setPricePreTax(car.getPrice());
        calculateRequest.setSalesTax(tax.getTax());
        return carCalculatorService.queryCalculator(calculateRequest);
    }

    private Serializable sendRequestAndReceiveResponseObject(java.io.Serializable request) {
        log.info("Sending " + request.toString());
        return (Serializable) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                storage_queue_routing_key,
                request
        );
    }
}
