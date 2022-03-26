package com.kbe.kompsys;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kbe.kompsys.domain.deserializer.CustomCarDeserializer;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.repository.CarRepository;
import com.kbe.kompsys.service.storage_update.StorageUpdateService;
import lombok.SneakyThrows;
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
    private StorageUpdateService storageUpdateService;

    @SneakyThrows
    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent applicationReadyEvent) {
        log.info("Initializing database with cars");
        importCars();
        storageUpdateService.forceUpdateCarRepo();
    }

    private void importCars() {
        InputStream jsonfile_cars = importJsonAsFile("cars.json");
        List<Car> cars = readCars(jsonfile_cars);
        carRepository.saveAll(cars);
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

