package com.kbe.kompsys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbe.kompsys.domain.dto.*;
import com.kbe.kompsys.domain.dto.calculate.CalculateRequest;
import com.kbe.kompsys.domain.dto.calculate.CalculateResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class CarCalculatorService {

    @Transactional
    CalculateResponse queryCalculator(CalculateRequest request) throws JsonProcessingException {
        WebClient client = WebClient.create();
        String uri = String.format("http://localhost:8080/calculate?price=%s&salesTax=%s",
                request.getPrice(), request.getSalesTax());
        WebClient.ResponseSpec responseSpec = client.get().uri(uri).retrieve();
        String jsonResponse = responseSpec.bodyToMono(String.class).block();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonResponse, CalculateResponse.class);
    }


}
