package com.kbe.storage.amqp;


import com.github.dergil.kompsys.dto.tax.*;
import com.github.dergil.kompsys.dto.update.UpdateStorage;
import com.kbe.storage.service.CarStorageServiceImpl;
import com.kbe.storage.service.TaxStorageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;


@Slf4j
@RabbitListener(id = "storage", queues = "storage", returnExceptions = "true")
public class Receiver {
  @Autowired
  private TaxStorageServiceImpl taxStorageService;
  @Autowired
  private CarStorageServiceImpl carStorageService;


  @RabbitHandler
  public TaxView create(CreateTaxRequest request) {
    log.info("Received " + request.toString());
    return taxStorageService.create(request);
  }

  @RabbitHandler
  public TaxView update(EditTaxRequest request) {
    log.info("Received " + request.toString());
    return taxStorageService.update(request);
  }

  @RabbitHandler
  public TaxView delete(DeleteTaxRequest request) {
    log.info("Received " + request.toString());
    return taxStorageService.delete(request);
  }

  @RabbitHandler
  public TaxView get(ReadTaxRequest request) {
    log.info("Received " + request.toString());
    return taxStorageService.get(request);
  }

  @RabbitHandler
  public TaxViewList getAll(ReadAllTaxesRequest request) {
    log.info("Received " + request.toString());
    return taxStorageService.getAll(request);
  }

  @RabbitHandler
  public boolean updateStorage(UpdateStorage request) throws FileNotFoundException {
    log.info("Received " + request.toString());
    return carStorageService.updateStorage(request);
  }
}
