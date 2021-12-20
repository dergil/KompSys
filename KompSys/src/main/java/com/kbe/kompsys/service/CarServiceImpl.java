package com.kbe.kompsys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kbe.kompsys.domain.dto.calculate.CalculateRequest;
import com.kbe.kompsys.domain.dto.calculate.CalculateResponse;
import com.kbe.kompsys.domain.dto.car.CarTaxCalculateView;
import com.kbe.kompsys.domain.dto.car.CarTaxRequest;
import com.kbe.kompsys.domain.dto.car.CarView;
import com.kbe.kompsys.domain.dto.car.EditCarRequest;
import com.kbe.kompsys.domain.dto.geolocation.GeolocationResponse;
import com.kbe.kompsys.domain.mapper.CalculateViewMapper;
import com.kbe.kompsys.domain.mapper.CarEditMapper;
import com.kbe.kompsys.domain.mapper.CarViewMapper;
import com.kbe.kompsys.domain.mapper.TaxViewMapper;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.domain.model.Tax;
import com.kbe.kompsys.repository.CarRepository;
import com.kbe.kompsys.repository.TaxRepository;
import com.kbe.kompsys.service.interfaces.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarEditMapper carEditMapper;
    @Autowired
    private CarViewMapper carViewMapper;
    @Autowired
    private CarRepository carRepository;

    @Transactional
    public CarView create(EditCarRequest request) {
        Car car = carEditMapper.create(request);
        carRepository.save(car);
        return carViewMapper.toCarView(car);
    }

    @Transactional
    public CarView update(long id, EditCarRequest request) {
        Car car = carRepository.getCarById(id);
        carEditMapper.update(request, car);
        car = carRepository.save(car);
        return carViewMapper.toCarView(car);
    }

    @Transactional
    public CarView delete(long id) {
        Car car = carRepository.getCarById(id);
        carRepository.delete(car);
        return carViewMapper.toCarView(car);
    }

    @Transactional
    public CarView get(long id) {
        Car car = carRepository.getCarById(id);
        return carViewMapper.toCarView(car);
    }

    @Transactional
    public List<CarView> getAll() {
        List<Car> cars = carRepository.findAll();
        List<CarView> carViews = new ArrayList<>();
        for (Car car : cars) {
            carViews.add(carViewMapper.toCarView(car));
        }
        return carViews;
    }
}
