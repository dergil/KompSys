//package kbe.gateway;
//
//import com.github.dergil.calculation.CalculateRequest;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.stereotype.Component;
//
//@Component
//class StatefulBlockingClient {
//
//    private final RabbitTemplate template;
//    private final DirectExchange directExchange;
//    public static final String ROUTING_KEY = "old.car";
//
//    @Autowired
//    StatefulBlockingClient(RabbitTemplate template, DirectExchange directExchange) {
//        this.template = template;
//        this.directExchange = directExchange;
//    }
//
//    public void send() {
//        CalculateRequest calculateRequest = new CalculateRequest(99000, 0.19);
//
//        template.convertSendAndReceiveAsType(
//                directExchange.getName(),
//                ROUTING_KEY,
//                calculateRequest,
//                new ParameterizedTypeReference<>() {
//                });
//    }
//}
//
