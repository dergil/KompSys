package com.kbe.kompsys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbe.kompsys.domain.dto.calculate.CalculateRequest;
import com.kbe.kompsys.domain.dto.calculate.CalculateResponse;
import com.kbe.kompsys.domain.dto.car.CarView;
import com.kbe.kompsys.domain.dto.car.EditCarRequest;
import com.kbe.kompsys.domain.dto.geolocation.GeolocationResponse;
import com.kbe.kompsys.domain.dto.tax.TaxRequest;
import com.kbe.kompsys.domain.dto.tax.TaxResponse;
import com.kbe.kompsys.domain.mapper.CarEditMapper;
import com.kbe.kompsys.domain.mapper.CarTaxResponseMapper;
import com.kbe.kompsys.domain.mapper.CarViewMapper;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//https://medium.com/echohub/spring-boot-redis-postgresql-caching-58ca352280a3

@Service
@CacheConfig(cacheNames = "carCache")
public class CarService {

    @Autowired
    private CarEditMapper carEditMapper;
    @Autowired
    private CarViewMapper carViewMapper;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarTaxResponseMapper carTaxResponseMapper;


    @Transactional
    @CacheEvict(cacheNames = "cars", allEntries = true)
    public CarView create(EditCarRequest request) {
        Car car = carEditMapper.create(request);
        carRepository.save(car);
        return carViewMapper.toCarView(car);
    }

    @Transactional
    @CacheEvict(cacheNames = "cars", allEntries = true)
    public CarView update(String id, EditCarRequest request) {
        Car car = carRepository.getCarById(id);
        carEditMapper.update(request, car);
        car = carRepository.save(car);
        return carViewMapper.toCarView(car);
    }

    @Transactional
    @Caching(evict = { @CacheEvict(cacheNames = "car", key = "#id"),
            @CacheEvict(cacheNames = "cars", allEntries = true) })
    public CarView delete(String id) {
        Car car = carRepository.getCarById(id);
        carRepository.delete(car);
        return carViewMapper.toCarView(car);
    }

    @Transactional
    @Cacheable(cacheNames = "car", key = "#id", unless = "#result == null")
    public CarView get(String id) {
        Car car = carRepository.getCarById(id);
        return carViewMapper.toCarView(car);
    }

    @Transactional
    @Cacheable(cacheNames = "customers")
    public List<CarView> getAll() {
        Iterable<Car> cars = carRepository.findAll();
        List<CarView> carViews = new ArrayList<>();
        for (Car car : cars) {
            carViews.add(carViewMapper.toCarView(car));
        }
        return carViews;
    }

    @Transactional
    public TaxResponse calculateTax(TaxRequest request) throws JsonProcessingException {
        Optional<Car> car = carRepository.findById(request.getId());
        if (car.isEmpty())
            return null;
        GeolocationResponse geolocationResponse = queryGeolocation(request.getIpAddr());
        double taxRate = queryTaxRate(geolocationResponse);
        CalculateRequest calculateRequest = new CalculateRequest();
        calculateRequest.setPrice(car.get().getPrice());
        calculateRequest.setSalesTax(taxRate);
        CalculateResponse calculateResponse = queryCalculator(calculateRequest);
//        todo: mapping klären
        Car foundCar = car.get();
        TaxResponse taxResponse = carTaxResponseMapper.create(foundCar);
        taxResponse.setSalesTax(calculateResponse.getSalesTax());
        taxResponse.setTaxAmount(calculateResponse.getTaxAmount());
        taxResponse.setCountryCode(geolocationResponse.getCountryCode());
        taxResponse.setRegion(geolocationResponse.getRegion());
        return taxResponse;
    }

    private CalculateResponse queryCalculator(CalculateRequest request) throws JsonProcessingException {
        WebClient client = WebClient.create();
        String uri = String.format("http://localhost:8080/calculate?price=%s&salesTax=%s",
                request.getPrice(), request.getSalesTax());
        WebClient.ResponseSpec responseSpec = client.get().uri(uri).retrieve();
        String jsonResponse = responseSpec.bodyToMono(String.class).block();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonResponse, CalculateResponse.class);
    }

    private GeolocationResponse queryGeolocation(String ipAddr) throws JsonProcessingException {
        if (ipAddr.equals("127.0.0.1"))
            ipAddr = "141.45.44.203";
        WebClient client = WebClient.create();
        String response = client.get()
                .uri("http://ip-api.com/json/" + ipAddr)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        GeolocationResponse geolocationResponse = new GeolocationResponse();
        geolocationResponse.setCountryCode(jsonNode.get("countryCode").asText());
        geolocationResponse.setRegion(jsonNode.get("region").asText());
        return geolocationResponse;
    }

    private double queryTaxRate(GeolocationResponse geolocationResponse) {
        if (geolocationResponse.getCountryCode().equals("DE"))
            return 19;
        else return 0;
    }
}

