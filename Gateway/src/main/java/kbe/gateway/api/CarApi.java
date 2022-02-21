package kbe.gateway.api;

import com.github.dergil.kompsys.dto.car.*;
import com.github.dergil.kompsys.dto.car.tax.CarTaxCalculateView;
import com.github.dergil.kompsys.dto.car.tax.CarTaxRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.server.reactive.ServerHttpRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/car")
public class CarApi {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;

    public CarApi(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    @PostMapping
    public CarView create(@RequestBody @Valid CreateCarRequest request){
        return transferRequest(request);
    }

    @GetMapping
    public CarView get(@RequestParam @Valid long id) {
        return transferRequest(new ReadCarRequest(id));
    }

    @GetMapping("/all")
    public CarViewList getAll() {
        return transferRequest(new ReadAllCarsRequest());
    }

    @PutMapping
    public CarView update(@RequestBody @Valid EditCarRequest request) {
        return transferRequest(request);
    }

    @DeleteMapping
    public CarView delete(@RequestBody @Valid DeleteCarRequest request) {
        return transferRequest(request);
    }

    @GetMapping("/tax")
    public CarTaxCalculateView tax(@RequestParam @Valid long id, ServerHttpRequest httpRequest) {
        CarTaxRequest carTaxRequest = new CarTaxRequest();
        carTaxRequest.setId(id);
        String ip = Objects.requireNonNull(httpRequest.getRemoteAddress()).getAddress().getHostAddress();
        log.info(ip);
        carTaxRequest.setIpAddress(ip);
        return transferRequest(carTaxRequest);
    }

    @Nullable
    private CarView transferRequest(java.io.Serializable request) {
        return (CarView) sendRequestAndReceiveResponseObject(request);
    }

    @Nullable
    private CarViewList transferRequest(ReadAllCarsRequest request) {
        return (CarViewList) sendRequestAndReceiveResponseObject(request);
    }

    @Nullable
    private CarTaxCalculateView transferRequest(CarTaxRequest request) {
        return (CarTaxCalculateView) sendRequestAndReceiveResponseObject(request);
    }

    private Serializable sendRequestAndReceiveResponseObject(java.io.Serializable request) {
        log.info("Sending " + request.toString());
        return  (Serializable) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                "car",
                request
        );
    }


}