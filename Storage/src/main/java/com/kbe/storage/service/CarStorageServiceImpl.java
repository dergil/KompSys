package com.kbe.storage.service;

import com.github.dergil.kompsys.dto.update.UpdateStorage;
import com.github.dergil.kompsys.dto.update.UpdateStorageResponse;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.kbe.storage.domain.model.Car;
import com.kbe.storage.repository.CarRepository;
import com.kbe.storage.service.interfaces.CarStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class CarStorageServiceImpl implements CarStorageService {

  private final String CSVPATH = "/home/spring/";
  private final String FILENAME = "file.txt";

  @Autowired
  private final CsvImportServiceImpl csvImportService;
  @Autowired
  private CarRepository carRepository;
  @Autowired
  private SftpServiceImpl sftpServiceImpl;

  public CarStorageServiceImpl(CsvImportServiceImpl csvImportService) {
    this.csvImportService = csvImportService;
  }

  @Override
  public UpdateStorageResponse updateStorage(UpdateStorage request) throws JSchException {

    try {
      sftpServiceImpl.getFile("/upload/" + FILENAME, CSVPATH);
      List<List<String>> csv_cars = csvImportService.importCsv(CSVPATH + FILENAME);
      List<Car> cars = readCars(csv_cars);
      log.info("Read cars from CSV file: " + cars.toString());
      carRepository.saveAll(cars);
      return new UpdateStorageResponse(request.getChangesMade());
    } catch (FileNotFoundException e) {
      return new UpdateStorageResponse(-1);
    }
  }

  private static List<Car> readCars(List<List<String>> csv_cars) {
    List<Car> cars = new ArrayList<>();
    for (int i = 1; i < csv_cars.size(); i++) {
      Car ecr = new Car();
      ecr.setId(Long.parseLong(csv_cars.get(i).get(0)));
      ecr.setName(csv_cars.get(i).get(1));
      ecr.setPrice(Integer.parseInt(csv_cars.get(i).get(2)));
      ecr.setMilesPerGallon(Double.parseDouble(csv_cars.get(i).get(3)));
      ecr.setCylinders(Integer.parseInt(csv_cars.get(i).get(4)));
      ecr.setDisplacement(Integer.parseInt(csv_cars.get(i).get(5)));
      ecr.setHorsepower(Integer.parseInt(csv_cars.get(i).get(6)));
      ecr.setWeightInPounds(Integer.parseInt(csv_cars.get(i).get(7)));
      ecr.setAcceleration(Double.parseDouble(csv_cars.get(i).get(8)));
      ecr.setYear(LocalDate.parse(csv_cars.get(i).get(9)));
      ecr.setOrigin(csv_cars.get(i).get(10));
      cars.add(ecr);
      System.out.println(ecr);
    }
    return cars;
  }
}
