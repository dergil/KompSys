package com.kbe.storage.service;


import com.kbe.storage.domain.dto.car.EditCarRequest;
import com.kbe.storage.domain.exception.NotFoundException;
import com.kbe.storage.domain.mapper.CarEditMapper;
import com.kbe.storage.domain.model.Car;
import com.kbe.storage.repository.CarRepository;
import com.kbe.storage.service.interfaces.CarStorageService;
import com.kbe.storage.service.interfaces.CsvImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class CarStorageServiceImpl implements CarStorageService {

    @Autowired
    private final CsvImportService csvImportService;
    @Autowired
    private final CarEditMapper carEditMapper;

    @Autowired
    private CarRepository carRepository;

    public CarStorageServiceImpl(CsvImportService csvImportService, CarEditMapper carEditMapper) {
        this.csvImportService = csvImportService;
        this.carEditMapper = carEditMapper;
    }

    @Transactional
    public Car create(EditCarRequest request) {
        Car car = carEditMapper.create(request);
        carRepository.save(car);
        return car;
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
        //todo: delete backup car
        return null;
    }


    public boolean updateDatabaseByCsv() throws FileNotFoundException {

        List<List<String>> carlist = csvImportService.importCsv("cars.csv");

        return false;
    }

    public boolean checkChecksum() {

        return true;
    }

    public String testChecksum(String filepath) throws IOException, NoSuchAlgorithmException {
        String filePath = "S:\\___Studium\\5.Semester\\Storage\\src\\main\\resources\\cars.csv";
        String checksum = ChecksumService.createChecksum(filePath);
        String filePath2 = "S:\\___Studium\\5.Semester\\Storage\\src\\main\\resources\\cars2.csv";
        String checksum2 = ChecksumService.createChecksum(filePath2);
        System.out.println(checksum);
        System.out.println(checksum2);
        return checksum;
    }
}
