package kbe.gateway.service;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;


@Service
@Slf4j
public class RabbitMqTransferService {

  private final RabbitTemplate rabbitTemplate;
  private final DirectExchange directExchange;
  @Value("${car_routing_key:car}")
  private String CAR_QUEUE_ROUTING_KEY;
  @Value("${calculate_routing_key:calculate}")
  private String CALCULATE_QUEUE_ROUTING_KEY;
  @Value("${storage_routing_key:storage}")
  private String STORAGE_QUEUE_ROUTING_KEY;

  @Autowired
  public RabbitMqTransferService(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
    this.rabbitTemplate = rabbitTemplate;
    this.directExchange = directExchange;
  }

  //  ////////////CarRequests////////////
//  @Nullable
//  public CarView transferRequest(ReadCarRequest request) {
//    return (CarView) sendRequestAndReceiveResponseObject(request, CAR_QUEUE_ROUTING_KEY);
//  }
//
//  @Nullable
//  public CarView transferRequest(CreateCarRequest request) {
//    return (CarView) sendRequestAndReceiveResponseObject(request, CAR_QUEUE_ROUTING_KEY);
//  }
//
//  @Nullable
//  public CarView transferRequest(EditCarRequest request) {
//    return (CarView) sendRequestAndReceiveResponseObject(request, CAR_QUEUE_ROUTING_KEY);
//  }
//
//  @Nullable
//  public CarView transferRequest(DeleteCarRequest request) {
//    return (CarView) sendRequestAndReceiveResponseObject(request, CAR_QUEUE_ROUTING_KEY);
//  }
//
//  @Nullable
//  public CarViewList transferRequest(ReadAllCarsRequest request) {
//    return (CarViewList) sendRequestAndReceiveResponseObject(request, CAR_QUEUE_ROUTING_KEY);
//  }
//
//  ////////////CalculateRequests////////////
//  @Nullable
//  public CarTaxCalculateView transferRequest(CarTaxRequest request) {
//    return (CarTaxCalculateView) sendRequestAndReceiveResponseObject(request, CAR_QUEUE_ROUTING_KEY);
//  }
//
  @Nullable
  public CalculateResponse transferRequest(CalculateRequest request) {
    return (CalculateResponse) sendRequestAndReceiveResponseObject(request);
  }

//  @Nullable
//  private TaxView transferRequest(java.io.Serializable request) {
//    return (TaxView) sendRequestAndReceiveResponseObject(request, STORAGE_QUEUE_ROUTING_KEY);
//  }

//  @Nullable
//  private TaxViewList transferRequest(ReadAllTaxesRequest request) {
//    return (TaxViewList) sendRequestAndReceiveResponseObject(request, STORAGE_QUEUE_ROUTING_KEY);
//  }

  @Nullable
  private Serializable sendRequestAndReceiveResponseObject(java.io.Serializable request) {
    log.info("Sending " + request.toString());
    return (Serializable) rabbitTemplate.convertSendAndReceive(
            directExchange.getName(),
            CALCULATE_QUEUE_ROUTING_KEY,
            request
    );
  }
}
