//package kbe.gateway;
//
//import com.github.dergil.calculation.CalculateRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class Runner implements CommandLineRunner {
//
//    private final RabbitTemplate rabbitTemplate;
//
//    public Runner(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Sending message...");
//        CalculateRequest calculateRequest = new CalculateRequest(99000, 0.19);
//        rabbitTemplate.convertAndSend("spring-boot-exchange", "foo.bar.baz", calculateRequest);
//    }
//
//}