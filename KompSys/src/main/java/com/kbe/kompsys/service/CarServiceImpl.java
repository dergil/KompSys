package com.kbe.kompsys.service;

import com.github.dergil.kompsys.dto.car.*;
import com.kbe.kompsys.domain.mapper.CarEditMapper;
import com.kbe.kompsys.domain.mapper.CarViewMapper;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.repository.interfaces.CarRepository;
import com.kbe.kompsys.service.interfaces.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarEditMapper carEditMapper;
    @Autowired
    private CarViewMapper carViewMapper;
    @Autowired
    private CarRepository carRepository;


    @Transactional
    public CarView create(CreateCarRequest request) {
        Car car = carEditMapper.create(request);
        carRepository.save(car);
        CarView carView = carViewMapper.toCarView(car);
        log.info("Returning: " + carView.toString());
        return carView;
    }

    @Transactional
    public CarView update(EditCarRequest request) {
        Car car = carRepository.getCarById(request.getId());
        carEditMapper.update(request, car);
        car = carRepository.save(car);
        CarView carView = carViewMapper.toCarView(car);
        log.info("Returning: " + carView.toString());
        return carView;
    }

    @Transactional
    public CarView delete(DeleteCarRequest request) {
        Car car = carRepository.getCarById(request.getId());
        carRepository.delete(car);
        CarView carView = carViewMapper.toCarView(car);
        log.info("Returning: " + carView.toString());
        return carView;
    }

    @Transactional
    public CarView get(ReadCarRequest request) {
        Car car = carRepository.getCarById(request.getId());
        CarView carView = carViewMapper.toCarView(car);
        log.info("Returning: " + carView.toString());
        return carView;
    }

    @Transactional
    public CarViewList getAll(ReadAllCarsRequest request) {
        List<Car> cars = carRepository.findAll();
        List<CarView> carViews = new ArrayList<>();
        for (Car car : cars) {
            carViews.add(carViewMapper.toCarView(car));
        }
        CarViewList carViewList = new CarViewList(carViews);
        log.info("Returning: " + carViewList);
        return carViewList;
    }
}
