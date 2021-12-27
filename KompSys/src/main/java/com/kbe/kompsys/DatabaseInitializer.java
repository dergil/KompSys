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
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private TaxRepository taxRepository;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        InputStream jsonfile_cars = importJsonAsFile("cars.json");
        List<Car> cars = readCars(jsonfile_cars);
        carRepository.saveAll(cars);

        InputStream jsonfile_taxes = importJsonAsFile("taxes.json");
        List<Tax> taxes = readTaxes(jsonfile_taxes);
        taxRepository.saveAll(taxes);
    }

    private InputStream importJsonAsFile(String jsonFilename) {
        InputStream file = null;
        try {
//            https://stackoverflow.com/questions/14876836/file-inside-jar-is-not-visible-for-spring
//            file = ResourceUtils.getFile("classpath:" + jsonFilename);
            file = new ClassPathResource(jsonFilename).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private List<Car> readCars(InputStream file) {
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

    private List<Tax> readTaxes(InputStream file) {
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

