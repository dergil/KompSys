package com.kbe.kompsys.api;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/ip")
public class IP {
    @GetMapping("/get")
    public @ResponseBody
    String get(HttpServletRequest httpRequest) throws JSONException {
//        String ipAddr =httpRequest.getRemoteAddr();
        String ipAddr = "141.45.44.203";
        WebClient client = WebClient.create();

        WebClient.ResponseSpec responseSpec = client.get()
                .uri("http://ip-api.com/json/" + ipAddr)
                .retrieve();
        String response = responseSpec.bodyToMono(String.class).block();
//        JSONObject jsonResponse = new JSONObject(response);
//        return (String) jsonResponse.get("country");
        return response;
    }

}
