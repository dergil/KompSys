package com.kbe.kompsys.amqp;

import com.github.dergil.kompsys.dto.car.CarView;
import com.github.dergil.kompsys.dto.car.CreateCarRequest;
import com.github.dergil.kompsys.dto.car.DeleteCarRequest;
import com.github.dergil.kompsys.dto.car.EditCarRequest;
import com.kbe.kompsys.service.interfaces.CarService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitListeners {
    private final CarService carService;

    public RabbitListeners(CarService carService) {
        this.carService = carService;
    }

    @RabbitListener(queues = "car")
    public CarView receive(CreateCarRequest request) {
        return carService.create(request);
    }

    @RabbitListener(queues = "car")
    public CarView receive(EditCarRequest request) {
        return carService.update(request);
    }

    @RabbitListener(queues = "car")
    public CarView receive(DeleteCarRequest request) {
        return carService.delete(request);
    }
}
