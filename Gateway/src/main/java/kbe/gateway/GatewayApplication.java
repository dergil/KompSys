package kbe.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class GatewayApplication {

//    @Bean
//    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(p -> p
//                        .path("/car/*")
//                        .uri("http://localhost:8081"))
//                .route(p -> p
//                        .path("/car")
//                        .uri("http://localhost:8081"))
//                .route(p -> p
//                        .path("/calculate")
//                        .uri("http://localhost:8080"))
//                .build();
//    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);

    }

    @RefreshScope
    @RestController
    class MessageRestController {

        @Value("${message:Hello default}")
        private String message;

        @RequestMapping("/message")
        String getMessage() {
            return message;
        }

    }
}
