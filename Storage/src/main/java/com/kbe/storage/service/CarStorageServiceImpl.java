package com.kbe.storage.service;

import com.github.dergil.kompsys.dto.update.UpdateStorage;
import com.github.dergil.kompsys.dto.update.UpdateStorageResponse;
import com.kbe.storage.domain.model.Car;
import com.kbe.storage.repository.interfaces.CarRepository;
import com.kbe.storage.service.interfaces.CarStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class CarStorageServiceImpl implements CarStorageService {

  private final String CSVPATH = "cars.csv";

  @Autowired
  private final CsvImportServiceImpl csvImportService;
  @Autowired
  private CarRepository carRepository;

  public CarStorageServiceImpl(CsvImportServiceImpl csvImportService) {
    this.csvImportService = csvImportService;
  }

  @Override
  public UpdateStorageResponse updateStorage(UpdateStorage request) throws FileNotFoundException {
    try {
      List<List<String>> csv_cars = csvImportService.importCsv(CSVPATH);
      List<Car> cars = readCars(csv_cars);
      carRepository.saveAll(cars);
      return new UpdateStorageResponse(request.changesMade);
    } catch (FileNotFoundException e) {
      return new UpdateStorageResponse(-1);
    }
  }

  private static List<Car> readCars(List<List<String>> csv_cars) {
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
}
