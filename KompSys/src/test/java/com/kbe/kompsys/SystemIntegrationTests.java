package com.kbe.kompsys;

import com.github.dergil.kompsys.dto.car.CarView;
import com.github.dergil.kompsys.dto.car.CreateCarRequest;
import com.github.dergil.kompsys.dto.car.DeleteCarRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

@Slf4j
public class SystemIntegrationTests {

    @ClassRule
    public static DockerComposeContainer compose;

    static {
        compose = new DockerComposeContainer<>(
                new File("src/test/resources/test-compose.yml"));
        compose.withExposedService("gateway", 8082);
        compose.withLogConsumer("gateway", new Slf4jLogConsumer(SystemIntegrationTests.log));
        compose.withLogConsumer("calculator", new Slf4jLogConsumer(SystemIntegrationTests.log));
        compose.withLogConsumer("main", new Slf4jLogConsumer(SystemIntegrationTests.log));
        compose.waitingFor("gateway", Wait.forLogMessage(".*Started GatewayApplication.*", 1));
        compose.waitingFor("calculator", Wait.forLogMessage(".*Started CalculatorApplication.*", 1));
        compose.waitingFor("main", Wait.forLogMessage(".*Started KompSysApplication.*", 1));
//        this is key; increase var 'times' when adding containers
        compose.waitingFor("rabbitmq", Wait.forLogMessage(".*granted access.*", 2));
    }

//    @Test
//    public void correctCalculateResponse_onCalculateRequest()
//            throws Exception {
//        String address = "http://"
//                + compose.getServiceHost("gateway", 8082) + ":"
//                + compose.getServicePort("gateway", 8082)
//                + "/calculate?pricePreTax=99000&salesTax=0.19";
//        String response = simpleGetRequest(address);
//        String correctResponse = "{\"pricePostTax\":117810.0,\"pricePreTax\":99000.0,\"salesTax\":0.19,\"taxAmount\":18810.0}";
//        Assertions.assertEquals(correctResponse, response);
//    }

//    @Test
//    public void getCar() throws Exception {
//        String address = "http://"
//                + compose.getServiceHost("gateway", 8082) + ":"
//                + compose.getServicePort("gateway", 8082)
//                + "/car?id=3";
//        String response = simpleGetRequest(address);
//        System.out.println("MYRESPONSE: " + response);
//        String correctResponse = "{\"id\":3,\"name\":\"plymouth satellite\",\"price\":40000,\"milesPerGallon\":18,\"cylinders\":8,\"displacement\":318,\"horsepower\":150,\"weightInPounds\":3436,\"acceleration\":11,\"year\":\"1970-01-01\",\"origin\":\"USA\"}";
//        Assertions.assertEquals(correctResponse, response);
//    }

    @Test
    public void deleteCar() {
        String address = "http://"
                + compose.getServiceHost("gateway", 8082) + ":"
                + compose.getServicePort("gateway", 8082);
        WebClient webClient = WebClient.create(address);
        CarView carView = webClient.delete()
                .uri("/car/3")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(CarView.class)
                .block();
        System.out.println("MYRESPONSE: " + carView);
    }

    private String simpleGetRequest(String address) throws Exception {
        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }
}
