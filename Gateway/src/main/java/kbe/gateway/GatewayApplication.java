package kbe.gateway;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class GatewayApplication {

//    @Bean
//    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(p -> p
//                        .path("/car/*")
//                        .uri("http://main:8081"))
//                .route(p -> p
//                        .path("/car")
//                        .uri("http://main:8081"))
//                .route(p -> p
//                        .path("/calculate")
//                        .uri("http://calculator:8080"))
//                .build();
//    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
