package com.kbe.kompsys.amqp;


import com.github.dergil.kompsys.dto.car.CarView;
import com.github.dergil.kompsys.dto.car.CreateCarRequest;
import com.github.dergil.kompsys.dto.car.DeleteCarRequest;
import com.github.dergil.kompsys.dto.car.EditCarRequest;
import com.kbe.kompsys.service.interfaces.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "car", returnExceptions = "true")
public class RabbitListeners {
    private final CarService carService;

    public RabbitListeners(CarService carService) {
        this.carService = carService;
    }

    @RabbitHandler
    public CarView handleCreateCarRequest(CreateCarRequest request) {
        log.info("Received " + request.toString());
        return carService.create(request);
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
}
