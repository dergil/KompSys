package com.kbe.kompsys.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kbe.kompsys.domain.dto.*;
import com.kbe.kompsys.domain.dto.CarTaxCalculateView;
import com.kbe.kompsys.service.CarCalculatorService;
import com.kbe.kompsys.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/car")
public class CarApi {

    @Autowired
    private CarService carService;
    @Autowired
    private CarCalculatorService carCalculatorService;


    @PostMapping("/create")
    public CarView create(@RequestBody @Valid EditCarRequest request) {
        return carService.create(request);
    }

    @GetMapping("/tax")
    public CarTaxCalculateView tax(@RequestParam(required = true) long id, HttpServletRequest httpRequest) throws JsonProcessingException {
        CarTaxRequest carTaxRequest = new CarTaxRequest();
        carTaxRequest.setId(id);
        carTaxRequest.setIpAddress(httpRequest.getRemoteAddr());
        return carService.queryCarTaxView(carTaxRequest);
    }
}
