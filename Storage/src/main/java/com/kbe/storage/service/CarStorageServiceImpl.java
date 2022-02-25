package com.kbe.storage.service;


import com.kbe.storage.domain.dto.car.EditCarRequest;
import com.kbe.storage.domain.exception.NotFoundException;
import com.kbe.storage.domain.mapper.CarEditMapper;
import com.kbe.storage.domain.model.Car;
import com.kbe.storage.repository.CarRepository;
import com.kbe.storage.service.interfaces.CarStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CarStorageServiceImpl implements CarStorageService {

  @Autowired
  private final CarEditMapper carEditMapper;


  @Autowired
  private CarRepository carRepository;

  public CarStorageServiceImpl(CarEditMapper carEditMapper, CarRepository carRepository) {
    this.carEditMapper = carEditMapper;
    this.carRepository = carRepository;

  }

  @Transactional
  public Car create(EditCarRequest request) {
    Car car = carEditMapper.create(request);
    carRepository.save(car);
    return car;
  }

  @Transactional
  public Car get(long id) {
    return carRepository.getCarById(id);
  }

  @Transactional
  public Car update(long id, EditCarRequest request) {
    Car car = null;
    try {
      car = carRepository.getCarById(id);
      carEditMapper.update(request, car);
      carRepository.save(car);
    } catch (NotFoundException e) {
      create(request);
    }
    return car;
  }

  @Transactional
  public Car delete(long id) {
    Car car = null;
    try {
      car = carRepository.getCarById(id);
      carRepository.deleteById(id);
    } catch (NotFoundException e) {
      return car;
    }
    return car; //todo: was zurueckgeben? string / car...
  }


}
