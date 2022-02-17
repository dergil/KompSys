//package kbe.gateway.api;
//
//
//import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
//import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@Slf4j
//@RestController
//public class CalculatorApi {
//    private final RabbitTemplate rabbitTemplate;
//    private final DirectExchange directExchange;
//    public static final String ROUTING_KEY = "calculate";
//
//    public CalculatorApi(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
//        this.rabbitTemplate = rabbitTemplate;
//        this.directExchange = directExchange;
//    }
//
//    @GetMapping("/calculate")
//    public CalculateResponse calculate(
//            @RequestParam double price,
//            @RequestParam double salesTax
//            ){
//        CalculateRequest calculateRequest = new CalculateRequest();
//        calculateRequest.setPrice(price);
//        calculateRequest.setSalesTax(salesTax);
//        log.info("Sending CalculateRequest");
//        CalculateResponse response = rabbitTemplate.convertSendAndReceiveAsType(
//                directExchange.getName(),
//                ROUTING_KEY,
//                calculateRequest,
//                new ParameterizedTypeReference<>() {
//                });
//
//        log.info("Received " + response.toString());
//        return response;
//    }
//}
