package com.kbe.kompsys.service.tax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dergil.kompsys.dto.geolocation.GeolocationResponse;
import com.kbe.kompsys.service.interfaces.GeolocationService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.reactive.function.client.WebClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Service
public class GeolocationServiceImpl implements GeolocationService {

    @Override
    public GeolocationResponse getGeolocation(String ipAddr) throws JsonProcessingException, UnknownHostException {
        log.info("Geolocating IP " + ipAddr);
        InetAddress inetAddress = validateIP(ipAddr);
        WebClient client = WebClient.create();
        String response = client.get()
                .uri("http://ip-api.com/json/" + inetAddress.getHostAddress())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        GeolocationResponse geolocationResponse = extractGeolocationResponse(response);
        log.info(geolocationResponse.toString());
        return geolocationResponse;
    }

    private InetAddress validateIP(String ipAddr) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(ipAddr);
        if (inetAddress.isSiteLocalAddress() || inetAddress.isLoopbackAddress()) {
//            university address
            return InetAddress.getByName("141.45.44.203");
        }
        return inetAddress;
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
