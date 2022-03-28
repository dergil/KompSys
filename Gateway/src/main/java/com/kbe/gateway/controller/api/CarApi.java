package com.kbe.gateway.controller.api;

import com.github.dergil.kompsys.dto.car.*;
import com.github.dergil.kompsys.dto.car.tax.CarTaxCalculateView;
import com.github.dergil.kompsys.dto.car.tax.CarTaxRequest;
import com.kbe.gateway.service.RabbitMqTransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/car")
public class CarApi {

  @Autowired
  private final RabbitMqTransferService transferService;
  @Value("${car_queue_routing_key:car}")
  private String car_queue_routing_key;

  public CarApi(RabbitMqTransferService transferService) {
    this.transferService = transferService;
  }

  @PostMapping
  public CarView create(@RequestBody @Valid CreateCarRequest request) {
    return (CarView) transferService.transferRequest(request, car_queue_routing_key);
  }

  @GetMapping
  public CarView get(@RequestParam @Valid long id) {
    return (CarView) transferService.transferRequest(new ReadCarRequest(id), car_queue_routing_key);
  }

  @GetMapping("/all")
  public CarViewList getAll() {
    return (CarViewList) transferService.transferRequest(new ReadAllCarsRequest(), car_queue_routing_key);
  }

  @PutMapping
  public CarView update(@RequestBody @Valid EditCarRequest request) {
    return (CarView) transferService.transferRequest(request, car_queue_routing_key);
  }

  @DeleteMapping
  public CarView delete(@RequestBody @Valid DeleteCarRequest request) {
    return (CarView) transferService.transferRequest(request, car_queue_routing_key);
  }

  @GetMapping("/tax")
  public CarTaxCalculateView tax(@RequestParam @Valid long id, HttpServletRequest httpRequest) {
    CarTaxRequest carTaxRequest = new CarTaxRequest();
    carTaxRequest.setId(id);
    String ip = Objects.requireNonNull(httpRequest.getRemoteAddr());
    carTaxRequest.setIpAddress(ip);
    return (CarTaxCalculateView) transferService.transferRequest(carTaxRequest, car_queue_routing_key);
  }

}
