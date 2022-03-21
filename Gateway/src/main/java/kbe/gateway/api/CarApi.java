package kbe.gateway.api;

import com.github.dergil.kompsys.dto.car.*;
import com.github.dergil.kompsys.dto.car.tax.CarTaxCalculateView;
import com.github.dergil.kompsys.dto.car.tax.CarTaxRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/car")
public class CarApi {

    @Value("${car_queue_routing_key:car}")
    private String car_queue_routing_key;

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;

    public CarApi(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    @PostMapping
    public CarView create(@RequestBody @Valid CreateCarRequest request){
        return (CarView) transferRequest(request);
    }

    @GetMapping
    public CarView get(@RequestParam @Valid long id) {
        return (CarView) transferRequest(new ReadCarRequest(id));
    }

    @GetMapping("/all")
    public CarViewList getAll() {
        return (CarViewList) transferRequest(new ReadAllCarsRequest());
    }

    @PutMapping
    public CarView update(@RequestBody @Valid EditCarRequest request) {
        return (CarView) transferRequest(request);
    }

    @DeleteMapping
    public CarView delete(@RequestBody @Valid DeleteCarRequest request) {
        return (CarView) transferRequest(request);
    }

    @GetMapping("/tax")
    public CarTaxCalculateView tax(@RequestParam @Valid long id, HttpServletRequest httpRequest) {
        CarTaxRequest carTaxRequest = new CarTaxRequest();
        carTaxRequest.setId(id);
        String ip = Objects.requireNonNull(httpRequest.getRemoteAddr());
        carTaxRequest.setIpAddress(ip);
        return (CarTaxCalculateView) transferRequest(carTaxRequest);
    }

    private Serializable transferRequest(java.io.Serializable request) {
        log.info("Sending " + request.toString());
        return  (Serializable) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                car_queue_routing_key,
                request
        );
    }
}
