//package kbe.gateway;
//
//import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
//import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class Runner2 implements CommandLineRunner {
//
//    private final RabbitTemplate template;
//    private final DirectExchange directExchange;
//    public static final String ROUTING_KEY = "calculate";
//
//    public Runner2(RabbitTemplate template, DirectExchange directExchange) {
//        this.template = template;
//        this.directExchange = directExchange;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Sending message...");
//        CalculateRequest calculateRequest = new CalculateRequest(99000, 0.19);
//        CalculateResponse response = template.convertSendAndReceiveAsType(
//                directExchange.getName(),
//                ROUTING_KEY,
//                calculateRequest,
//                new ParameterizedTypeReference<>() {
//                });
//        System.out.println(response);
//    }
//
//}