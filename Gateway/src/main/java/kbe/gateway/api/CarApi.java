package kbe.gateway.api;

import com.github.dergil.kompsys.dto.car.CarView;
import com.github.dergil.kompsys.dto.car.CreateCarRequest;
import com.github.dergil.kompsys.dto.car.DeleteCarRequest;
import com.github.dergil.kompsys.dto.car.EditCarRequest;
import com.github.dergil.kompsys.dto.temp.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.RemoteInvocationAwareMessageConverterAdapter;
import org.springframework.core.ParameterizedTypeReference;
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


//    @PostMapping
//    public Person person(@RequestParam String name){
//        System.out.println("Sending person...");
//        rabbitTemplate.setMessageConverter(new RemoteInvocationAwareMessageConverterAdapter());
////        try {
////        rabbitTemplate.convertSendAndReceiveAsType("my-exchange", "foo.bar.baz", new Person("John"), new ParameterizedTypeReference<Person>() {});
//        return (Person) rabbitTemplate.convertSendAndReceive("my-exchange", "foo.bar.baz", new Person("John"));
////        } catch (Exception e){
////            e.printStackTrace();
////        }
//    }

    @PostMapping
    public CarView create(@RequestBody @Valid CreateCarRequest request){
        rabbitTemplate.setMessageConverter(new RemoteInvocationAwareMessageConverterAdapter());
        log.info("Sending CreateCarRequest" + request.toString());
        return (CarView) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                "create_car",
                request
        );
    }

    @PutMapping()
    public CarView update(@RequestBody @Valid EditCarRequest request) {
        rabbitTemplate.setMessageConverter(new RemoteInvocationAwareMessageConverterAdapter());
        log.info("Sending EditCarRequest" + request.toString());
        return (CarView) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                "update_car",
                request);
    }

    @DeleteMapping()
    public CarView delete(@RequestBody @Valid DeleteCarRequest request) {
        rabbitTemplate.setMessageConverter(new RemoteInvocationAwareMessageConverterAdapter());
        log.info("Sending DeleteCarRequest" + request.toString());
        return (CarView) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                "delete_car",
                request
              );
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
