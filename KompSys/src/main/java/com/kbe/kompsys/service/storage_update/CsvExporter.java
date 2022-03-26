package com.kbe.kompsys.service.storage_update;

import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class CsvExporter {

    @Autowired
    private CarRepository carRepository;

    public void exportCarsToCSV() throws IOException {
        String[] csvHeader = {
                "Id",
                "Name",
                "Price",
                "MilesPerGallon",
                "Cylinders",
                "Displacement",
                "Horsepower",
                "WeightInPounds",
                "Acceleration",
                "Year",
                "Origin",};

        String[] nameMapping = {
                "id",
                "name",
                "price",
                "milesPerGallon",
                "cylinders",
                "displacement",
                "horsepower",
                "weightInPounds",
                "acceleration",
                "year",
                "origin",};

        exportToCSV(csvHeader, nameMapping, "Car");
    }

    private void exportToCSV(String[] csvHeader, String[] nameMapping, String dto) throws IOException {
        log.info("Attempting csv export");
        ICsvBeanWriter csvWriter = new CsvBeanWriter(new FileWriter("/home/spring/csv/cars.csv"), CsvPreference.STANDARD_PREFERENCE);
        csvWriter.writeHeader(csvHeader);

        if ("Car".equals(dto)) {
            List<Car> listCars = carRepository.findAll();
            for (Car car : listCars) {
                csvWriter.write(car, nameMapping);
            }
        }
        csvWriter.close();
        log.info("CSV writer closed");
    }
}

