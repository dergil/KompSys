package com.kbe.storage;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.dergil.kompsys.dto.update.UpdateStorage;
import com.kbe.storage.domain.model.Tax;
import com.kbe.storage.repository.TaxRepository;
import com.kbe.storage.service.interfaces.CarStorageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {


  @Autowired
  private TaxRepository taxRepository;
  @Autowired
  private CarStorageService carStorageService;


  @SneakyThrows
  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
    // todo flag setzen, nur updaten wenn flag vorhanden. hidden file mit True/False ?

    carStorageService.updateStorage(new UpdateStorage());
    importTaxes();
  }


  private InputStream importJsonAsFile(String jsonFilename) {
    InputStream file = null;
    try {
      file = new ClassPathResource(jsonFilename).getInputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return file;
  }

  private void importTaxes() {
    log.info("importing taxes ");
    InputStream jsonfile_taxes = importJsonAsFile("taxes.json");
    List<Tax> taxes = readTaxes(jsonfile_taxes);
    taxRepository.saveAll(taxes);
  }

  private List<Tax> readTaxes(InputStream file) {
    log.info("reading taxes ");
    ObjectMapper mapper = configureTaxMapper();
    return mapFromJsonToList(file, mapper, Tax.class);
  }

  @NotNull
  private ObjectMapper configureTaxMapper() {
    log.info("configure taxmapper ");
    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module =
            new SimpleModule("CustomTaxDeserializer",
                    new Version(1, 0, 0, null, null, null));
    module.addDeserializer(Tax.class, new CustomTaxDeserializer());
    mapper.registerModule(module);
    return mapper;
  }

  @Nullable
  private <T> List<T> mapFromJsonToList(InputStream file, ObjectMapper mapper, Class<T> contentClass) {
    List<T> objects = null;
    try {
      JavaType type = mapper.getTypeFactory().constructParametricType(List.class, contentClass);
      objects = mapper.readValue(file, type);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return objects;
  }
}

