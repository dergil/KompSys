package com.kbe.kompsys.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kbe.kompsys.domain.dto.CarView;
import com.kbe.kompsys.domain.dto.EditCarRequest;
import com.kbe.kompsys.domain.dto.TaxRequest;
import com.kbe.kompsys.domain.dto.TaxResponse;
import com.kbe.kompsys.domain.exception.NotFoundException;
import com.kbe.kompsys.domain.model.Car;
import com.kbe.kompsys.repository.CarRepository;
import com.kbe.kompsys.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/car")
public class CarApi {

    @Autowired
    private CarService carService;

    @PostMapping()
    public CarView create(@RequestBody @Valid EditCarRequest request){
        return carService.create(request);
    }

    @PutMapping("{id}")
    public CarView update(@PathVariable long id, @RequestBody @Valid EditCarRequest request){
        try {
            return carService.update(id, request);
        } catch (NotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public CarView delete(@PathVariable long id){
        return carService.delete(id);
    }

    @GetMapping("{id}")
    public CarView get(@PathVariable long id){
        return carService.get(id);
    }

    @GetMapping()
    public List<CarView> getAll(){
        return carService.getAll();
    }

    @GetMapping("/tax")
    public TaxResponse tax(@RequestParam(required = true) long id, HttpServletRequest httpRequest) throws JsonProcessingException {
        TaxRequest taxRequest = new TaxRequest();
        taxRequest.setId(id);
        taxRequest.setIpAddr(httpRequest.getRemoteAddr());
        return carService.calculateTax(taxRequest);
    }
}
