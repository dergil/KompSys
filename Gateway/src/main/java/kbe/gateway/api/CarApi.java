package kbe.gateway.api;

import com.github.dergil.kompsys.dto.car.CarView;
import com.github.dergil.kompsys.dto.car.CreateCarRequest;
import com.github.dergil.kompsys.dto.car.DeleteCarRequest;
import com.github.dergil.kompsys.dto.car.EditCarRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/car")
public class CarApi {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;
    public static final String CAR_ROUTING_KEY = "car";

    public CarApi(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    @PostMapping
    public CarView create(@RequestBody @Valid CreateCarRequest request){
        log.info("Sending CreateCarRequest");
        CarView response = rabbitTemplate.convertSendAndReceiveAsType(
                directExchange.getName(),
                CAR_ROUTING_KEY,
                request,
                new ParameterizedTypeReference<>() {
                });

        log.info("Received " + response.toString());
        return response;
    }

    @PutMapping()
    public void update(@RequestBody @Valid EditCarRequest request) {
        log.info("Sending EditCarRequest");
        try {
            rabbitTemplate.convertSendAndReceive(
                    directExchange.getName(),
                    CAR_ROUTING_KEY,
                    request);
        } catch (Exception e){
            e.printStackTrace();
        }

//        log.info("Received " + response.toString());
//        return response;
    }

    @DeleteMapping()
    public CarView delete(DeleteCarRequest request) {
        log.info("Sending DeleteCarRequest");
        CarView response = rabbitTemplate.convertSendAndReceiveAsType(
                directExchange.getName(),
                CAR_ROUTING_KEY,
                request,
                new ParameterizedTypeReference<>() {
                });

        log.info("Received " + response.toString());
        return response;
    }

//    @GetMapping("{id}")
//    public CarView get(@PathVariable long id) {
//        return carServiceImpl.get(id);
//    }
//
//    @GetMapping()
//    public List<CarView> getAll() {
//        return carServiceImpl.getAll();
//    }
//
//    @GetMapping("/tax")
//    public CarTaxCalculateView tax(@RequestParam(required = true) long id, HttpServletRequest httpRequest) throws JsonProcessingException {
//        CarTaxRequest carTaxRequest = new CarTaxRequest();
//        carTaxRequest.setId(id);
//        carTaxRequest.setIpAddress(httpRequest.getRemoteAddr());
//        return taxService.queryCarTaxView(carTaxRequest);
//    }
}
