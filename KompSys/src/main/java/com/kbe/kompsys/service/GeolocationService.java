package com.kbe.kompsys.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dergil.kompsys.dto.geolocation.GeolocationResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeolocationService {

    GeolocationResponse getGeolocation(String ipAddr) throws JsonProcessingException {
        if (ipAddr.equals("127.0.0.1") || ipAddr.equals("0:0:0:0:0:0:0:1"))
            ipAddr = "141.45.44.203";
        WebClient client = WebClient.create();
        String response = client.get()
                .uri("http://ip-api.com/json/" + ipAddr)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return extractGeolocationResponse(response);
    }

    @NotNull
    private GeolocationResponse extractGeolocationResponse(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        return constructGeolocationResponse(jsonNode);
    }

    @NotNull
    private GeolocationResponse constructGeolocationResponse(JsonNode jsonNode) {
        GeolocationResponse geolocationResponse = new GeolocationResponse();
        geolocationResponse.setCountryCode(jsonNode.get("countryCode").asText());
        geolocationResponse.setRegion(jsonNode.get("region").asText());
        return geolocationResponse;
    }
}
