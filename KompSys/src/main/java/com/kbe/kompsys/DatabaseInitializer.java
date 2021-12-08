package com.kbe.kompsys;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kbe.kompsys.domain.dto.TaxView;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.repository.CarRepository;
import com.kbe.kompsys.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
//import com.kbe.kompsys.domain.model.Car;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private CarRepository carRepository;
    //@Autowired
   // private TaxRepository taxRepository;


//    @Autowired
//    private TestService testService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        File jsonfile = importJsonAsFile("cars.json");
        List<Car> cars = readCars(jsonfile);
        carRepository.saveAll(cars);




//        testService.test();
    }

    private File importJsonAsFile(String jsonFilename){
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:" + jsonFilename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

    private List<Car> readCars(File file){
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomCarDeserializer",
                        new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Car.class, new CustomCarDeserializer());
        mapper.registerModule(module);

        List<Car> cars = null;
        try {
            cars = mapper.readValue(file, new TypeReference<>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cars;
    }




}

