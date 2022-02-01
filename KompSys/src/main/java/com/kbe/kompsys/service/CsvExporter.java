package com.kbe.kompsys.service;

import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class CsvExporter {

    @Autowired
    private CarService service;
    @Autowired
    private CarRepository carRepository;
//    @Autowired
//    private TaxRepository taxRepository;

    @GetMapping("/cars/export")
    public void exportCarsToCSV(HttpServletResponse response) throws IOException {
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

        exportToCSV(response, csvHeader, nameMapping, "Car");

    }

    @GetMapping("/tax/export")
    public void exportTaxToCSV(HttpServletResponse response) throws IOException {
        String[] csvHeader = {
                "Country",
                "Tax"};
        String[] nameMapping = {
                "countryCodeID",
                "tax"};

        exportToCSV(response, csvHeader, nameMapping, "Tax");
    }


    public void exportToCSV(HttpServletResponse response, String[] csvHeader, String[] nameMapping, String dto) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        csvWriter.writeHeader(csvHeader);

        switch (dto) {
            case "Car":
                List<Car> listCars = carRepository.findAll(); // todo Crud in CarService sollte Car returnen nicht die View direkt, hier dann Crud CarService.Getall()
                for (Car car : listCars) {
                    csvWriter.write(car, nameMapping);
                }
            case "Tax":
//                List<Tax> listTax = taxRepository.findAll();
//                for (Tax tax : listTax) {
//                    csvWriter.write(tax, nameMapping);
//                }
        }
        csvWriter.close();

    }

}

