package com.kbe.kompsys.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kbe.kompsys.domain.dto.CarView;
import com.kbe.kompsys.domain.dto.EditCarRequest;
import com.kbe.kompsys.domain.dto.TaxRequest;
import com.kbe.kompsys.domain.dto.TaxResponse;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.repository.CarRepository;
import com.kbe.kompsys.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(path = "/car")
public class CarApi {

    @Autowired
    private CarService carService;
    @Autowired
    private CarRepository carRepository;

    @PostMapping("/create")
    public CarView create(@RequestBody @Valid EditCarRequest request){
        return carService.create(request);
    }

    @GetMapping("/tax")
    public TaxResponse tax(@RequestParam(required = true) long id, HttpServletRequest httpRequest) throws JsonProcessingException {
        TaxRequest taxRequest = new TaxRequest();
        taxRequest.setId(id);
        taxRequest.setIpAddr(httpRequest.getRemoteAddr());
        return carService.calculateTax(taxRequest);
    }
}
