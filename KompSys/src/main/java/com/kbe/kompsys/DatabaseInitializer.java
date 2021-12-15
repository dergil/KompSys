package com.kbe.kompsys;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.domain.model.Tax;
import com.kbe.kompsys.repository.CarRepository;
import com.kbe.kompsys.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private TaxRepository taxRepository;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        File jsonfile_cars = importJsonAsFile("cars.json");
        List<Car> cars = readCars(jsonfile_cars);
        carRepository.saveAll(cars);

        File jsonfile_taxes = importJsonAsFile("taxes.json");
        List<Tax> taxes = readTaxes(jsonfile_taxes);
        taxRepository.saveAll(taxes);
    }

    private File importJsonAsFile(String jsonFilename) {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:" + jsonFilename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

    private List<Car> readCars(File file) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomCarDeserializer",
                        new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Car.class, new CustomCarDeserializer());
        mapper.registerModule(module);

        List<Car> cars = null;
        try {
            cars = mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    private List<Tax> readTaxes(File file) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomTaxDeserializer",
                        new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Tax.class, new CustomTaxDeserializer());
        mapper.registerModule(module);

        List<Tax> taxes = null;
        try {
            taxes = mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taxes;
    }
}

