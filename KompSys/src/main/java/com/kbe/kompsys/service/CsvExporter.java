package com.kbe.kompsys.service;

import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.repository.interfaces.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
//@Controller
@Service
public class CsvExporter {

    @Autowired
    private CarRepository carRepository;

//    @GetMapping("/cars/export")
    public void exportCarsToCSV() throws IOException {
        String[] csvHeader = {
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

//    @GetMapping("/tax/export")
//    public void exportTaxToCSV() throws IOException {
//        String[] csvHeader = {
//                "Country",
//                "Tax"};
//        String[] nameMapping = {
//                "countryCodeID",
//                "tax"};
//
//        exportToCSV(csvHeader, nameMapping, "Tax");
//    }


    private void exportToCSV(String[] csvHeader, String[] nameMapping, String dto) throws IOException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
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

