package com.kbe.kompsys.service;

import com.github.dergil.kompsys.dto.car.CarView;
import com.github.dergil.kompsys.dto.car.CreateCarRequest;
import com.github.dergil.kompsys.dto.car.DeleteCarRequest;
import com.github.dergil.kompsys.dto.car.EditCarRequest;
import com.kbe.kompsys.domain.mapper.CarEditMapper;
import com.kbe.kompsys.domain.mapper.CarViewMapper;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.repository.CarRepository;
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
    public CarView create(CreateCarRequest request) {
        Car car = carEditMapper.create(request);
        carRepository.save(car);
        return carViewMapper.toCarView(car);
    }

    @Transactional
    public CarView update(EditCarRequest request) {
//        TODO: mustn't create new car on request with invalid id
        Car car = carRepository.getCarById(request.getId());
        carEditMapper.update(request, car);
        car = carRepository.save(car);
        return carViewMapper.toCarView(car);
//        throw new NotFoundException(Car.class, request.getId());
    }

    @Transactional
    public CarView delete(DeleteCarRequest request) {
//        TODO: return only id, not empty carview; at FEB17 it retunred full carview with apparently deleted car
        Car car = carRepository.getCarById(request.getId());
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
