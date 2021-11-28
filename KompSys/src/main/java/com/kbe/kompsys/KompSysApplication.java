package com.kbe.kompsys;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class KompSysApplication {
    public static void main(String[] args) {
        SpringApplication.run(KompSysApplication.class, args);

    }
}
