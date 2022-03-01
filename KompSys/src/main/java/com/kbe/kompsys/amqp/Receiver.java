package com.kbe.kompsys.amqp;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.dergil.kompsys.dto.car.*;
import com.github.dergil.kompsys.dto.car.tax.CarTaxCalculateView;
import com.github.dergil.kompsys.dto.car.tax.CarTaxRequest;
import com.kbe.kompsys.service.interfaces.CarService;
import com.kbe.kompsys.service.interfaces.TaxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;
import java.util.List;

@Slf4j
@RabbitListener(id = "foo", queues = "car", returnExceptions = "true")
public class Receiver {
    @Autowired
    private CarService carService;
    @Autowired
    private TaxService taxService;

    @RabbitHandler
    public CarView handleCreateCarRequest(CreateCarRequest request) {
        log.info("Received " + request.toString());
        return carService.create(request);
    }

    @RabbitHandler
    public CarView handleReadCarRequest(ReadCarRequest request) {
        log.info("Received " + request.toString());
        return carService.get(request);
    }

    @RabbitHandler
    public CarViewList handleReadAllCarsRequest(ReadAllCarsRequest request) {
        log.info("Received " + request.toString());
        return carService.getAll(request);
    }

    @RabbitHandler
    public CarView handleEditCarRequest(EditCarRequest request) {
        log.info("Received " + request.toString());
        return carService.update(request);
    }

    @RabbitHandler
    public CarView handleDeleteCarRequest(DeleteCarRequest request) {
        log.info("Received " + request.toString());
        return carService.delete(request);
    }

    @RabbitHandler
    @Transactional
    public CarTaxCalculateView handleCarTaxRequest(CarTaxRequest request) throws JsonProcessingException, UnknownHostException {
        log.info("Received " + request.toString());
        return taxService.queryCarTaxView(request);
    }
}
