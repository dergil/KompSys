package com.kbe.storage.service;

import com.kbe.storage.service.interfaces.CsvImportService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class CsvImportServiceImpl implements CsvImportService {


    public List<List<String>> importCsv(String filename) throws FileNotFoundException {

        List<List<String>> cars = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader("S:\\___Studium\\5.Semester\\Storage\\src\\main\\resources\\cars.csv"))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                cars.add(Arrays.asList(values));
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return cars;
    }

}
