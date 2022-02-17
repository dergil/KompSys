package com.kbe.kompsys.amqp;


import com.github.dergil.kompsys.dto.car.CarView;
import com.github.dergil.kompsys.dto.car.CreateCarRequest;
import com.github.dergil.kompsys.dto.car.DeleteCarRequest;
import com.github.dergil.kompsys.dto.car.EditCarRequest;
import com.kbe.kompsys.service.interfaces.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitListeners {
    private final CarService carService;

    public RabbitListeners(CarService carService) {
        this.carService = carService;
    }

    @RabbitListener(queues = "create_car")
    public CarView receive(CreateCarRequest request) {
        log.info("Recevied as CreateCarRequest");
        return carService.create(request);
    }

    @RabbitListener(queues = "update_car", returnExceptions = "true")
    public CarView receive(EditCarRequest request) {
        log.info("Recevied as EditCarRequest"+ request.toString());
        return carService.update(request);
    }

    @RabbitListener(queues = "delete_car", returnExceptions = "true")
    public CarView receive(DeleteCarRequest request) {
        log.info("Recevied as DeleteCarRequest: " + request.toString());
        return carService.delete(request);
    }
}
