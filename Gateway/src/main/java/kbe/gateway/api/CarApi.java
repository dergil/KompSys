package kbe.gateway.api;

import com.github.dergil.kompsys.dto.car.CarView;
import com.github.dergil.kompsys.dto.car.CreateCarRequest;
import com.github.dergil.kompsys.dto.car.DeleteCarRequest;
import com.github.dergil.kompsys.dto.car.EditCarRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        log.info("Sending " + request.toString());
        return (CarView) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                "car",
                request
        );
    }

    @PutMapping()
    public CarView update(@RequestBody @Valid EditCarRequest request) {
        log.info("Sending " + request.toString());
        return (CarView) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                "car",
                request);
    }

    @DeleteMapping()
    public CarView delete(@RequestBody @Valid DeleteCarRequest request) {
        log.info("Sending " + request.toString());
        return (CarView) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                "car",
                request
              );
    }
}
