package com.kbe.storage.service.interfaces;


import java.io.FileNotFoundException;
import java.util.List;

public interface CsvImportService {
    List<List<String>> importCsv(String filename) throws FileNotFoundException;
}
