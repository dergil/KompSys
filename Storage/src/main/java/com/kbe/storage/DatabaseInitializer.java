package com.kbe.storage;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kbe.storage.domain.model.Tax;
import com.kbe.storage.repository.CarRepository;
import com.kbe.storage.repository.TaxRepository;
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
        //File csvfile_cars = importCsvAsFile("cars.csv");
        //List<Car> cars = readCars(csv_cars);
        //carRepository.saveAll(cars);

        File jsonfile_taxes = importJsonAsFile("taxes.json");
        List<Tax> tax = readTaxes(jsonfile_taxes);
        taxRepository.saveAll(tax);
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

    private List<Tax> readTaxes(File file) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomTaxDeserializer",
                        new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Tax.class, new CustomTaxDeserializer());
        mapper.registerModule(module);

        List<Tax> taxViews = null;
        try {
            taxViews = mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taxViews;
    }
}

