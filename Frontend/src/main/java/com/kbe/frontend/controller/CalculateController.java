package com.kbe.frontend.controller;

import com.github.dergil.kompsys.dto.calculate.CalculateRequest;
import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import com.kbe.frontend.factory.UriFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/calculate")
public class CalculateController {

  @GetMapping()
  public String showCalculateForm(Model model) {
    model.addAttribute("calculateRequest", new CalculateRequest());
    return "calculate_form";
  }

  @PostMapping()
  public String calculateRequest(@RequestParam double pricePreTax, @RequestParam double salesTax, Model model) {

    List<NameValuePair> queryParams = new ArrayList<NameValuePair>();
    queryParams.add(new BasicNameValuePair("pricePreTax", String.valueOf(pricePreTax)));
    queryParams.add(new BasicNameValuePair("salesTax", String.valueOf(salesTax)));

    URIBuilder builder = UriFactory.buildUri("/api/v1/calculate", queryParams);

    CalculateResponse apiResponse = WebClient.create()
            .get()
            .uri(builder.toString())
            .retrieve()
            .bodyToMono(CalculateResponse.class)
            .block();

    log.info(builder.toString());

    model.addAttribute("calculateResponse", apiResponse);
    return "calculate";
  }
}
