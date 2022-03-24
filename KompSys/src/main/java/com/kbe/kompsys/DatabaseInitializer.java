package com.kbe.kompsys;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kbe.kompsys.domain.deserializer.CustomCarDeserializer;
import com.kbe.kompsys.domain.deserializer.CustomTaxDeserializer;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.domain.model.Tax;
import com.kbe.kompsys.repository.CarRepository;
import com.kbe.kompsys.repository.TaxRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private TaxRepository taxRepository;


    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent applicationReadyEvent) {
        log.info("Initializing database with cars");
        importCars();
        importTaxes();
    }

    private void importCars() {
        InputStream jsonfile_cars = importJsonAsFile("cars.json");
        List<Car> cars = readCars(jsonfile_cars);
        carRepository.saveAll(cars);
    }

    private void importTaxes() {
        InputStream jsonfile_taxes = importJsonAsFile("taxes.json");
        List<Tax> taxes = readTaxes(jsonfile_taxes);
        taxRepository.saveAll(taxes);
    }

    //    https://stackoverflow.com/questions/14876836/file-inside-jar-is-not-visible-for-spring
    private InputStream importJsonAsFile(String jsonFilename) {
        InputStream file = null;
        try {
            file = new ClassPathResource(jsonFilename).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private List<Car> readCars(InputStream file) {
        ObjectMapper mapper = configureCarMapper();
        return mapFromJsonToList(file, mapper, Car.class);
    }

    private List<Tax> readTaxes(InputStream file) {
        ObjectMapper mapper = configureTaxMapper();
        return mapFromJsonToList(file, mapper, Tax.class);
    }

    @NotNull
    private ObjectMapper configureCarMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomCarDeserializer",
                        new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Car.class, new CustomCarDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    @NotNull
    private ObjectMapper configureTaxMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomTaxDeserializer",
                        new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Tax.class, new CustomTaxDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    //    https://stackoverflow.com/questions/11664894/jackson-deserialize-using-generic-class
    @Nullable
    private <T> List<T> mapFromJsonToList(InputStream file, ObjectMapper mapper, Class<T> contentClass) {
        List<T> objects = null;
        try {
            JavaType type = mapper.getTypeFactory().constructParametricType(List.class, contentClass);
            objects = mapper.readValue(file, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objects;
    }
}

