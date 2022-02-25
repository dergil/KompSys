package com.kbe.storage;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kbe.storage.domain.model.Car;
import com.kbe.storage.domain.model.Tax;
import com.kbe.storage.repository.CarRepository;
import com.kbe.storage.repository.TaxRepository;
import com.kbe.storage.service.CsvImportServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

  @Autowired
  private CarRepository carRepository;
  @Autowired
  private TaxRepository taxRepository;
  @Autowired
  private CsvImportServiceImpl csvImportServiceImpl;


  @SneakyThrows
  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    // todo flag setzen, nur updaten wenn flag vorhanden. hidden file mit True/False ?
    List<List<String>> csv_cars = csvImportServiceImpl.importCsv("S:\\\\___Studium\\\\5.Semester\\\\Storage\\\\src\\\\main\\\\resources\\\\cars.csv");
    List<Car> cars = readCars(csv_cars);
    carRepository.saveAll(cars);

    File jsonfile_taxes = importJsonAsFile("taxes.json");
    List<Tax> tax = readTaxes(jsonfile_taxes);
    taxRepository.saveAll(tax);
  }

  private List<Car> readCars(List<List<String>> csv_cars) {
    List<Car> cars = new ArrayList<>();
    for (int i = 1; i < csv_cars.size(); i++) {
      Car ecr = new Car();
      ecr.setName(csv_cars.get(i).get(0));
      ecr.setPrice(Integer.parseInt(csv_cars.get(i).get(1)));
      ecr.setMilesPerGallon(Double.parseDouble(csv_cars.get(i).get(2)));
      ecr.setCylinders(Integer.parseInt(csv_cars.get(i).get(3)));
      ecr.setDisplacement(Integer.parseInt(csv_cars.get(i).get(4)));
      ecr.setHorsepower(Integer.parseInt(csv_cars.get(i).get(5)));
      ecr.setWeightInPounds(Integer.parseInt(csv_cars.get(i).get(6)));
      ecr.setAcceleration(Double.parseDouble(csv_cars.get(i).get(7)));
      ecr.setYear(LocalDate.parse(csv_cars.get(i).get(8)));
      ecr.setOrigin(csv_cars.get(i).get(9));
      cars.add(ecr);
      System.out.println(ecr);
    }
    return cars;
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

