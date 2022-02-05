//package com.kbe.kompsys.api;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.kbe.kompsys.domain.dto.car.CarTaxCalculateView;
//import com.kbe.kompsys.domain.dto.car.CarTaxRequest;
//import com.kbe.kompsys.domain.dto.car.CarView;
//import com.kbe.kompsys.domain.dto.car.EditCarRequest;
//import com.kbe.kompsys.service.CarCalculatorService;
//import com.kbe.kompsys.service.CarServiceImpl;
//import com.kbe.kompsys.service.TaxServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//import java.util.List;
//
//@RestController
//@RequestMapping(path = "/car")
//public class CarApi {
//
//    @Autowired
//    private CarServiceImpl carServiceImpl;
//    @Autowired
//    private TaxServiceImpl taxService;
//
//
//    @PostMapping()
//    public CarView create(@RequestBody @Valid EditCarRequest request) {
//        return carServiceImpl.create(request);
//    }
//
//    @PutMapping("{id}")
//    public CarView update(@PathVariable long id, @RequestBody @Valid EditCarRequest request) {
//        return carServiceImpl.update(id, request);
//    }
//
//    @DeleteMapping("{id}")
//    public CarView delete(@PathVariable long id) {
//        return carServiceImpl.delete(id);
//    }
//
//    @GetMapping("{id}")
//    public CarView get(@PathVariable long id) {
//        return carServiceImpl.get(id);
//    }
//
//    @GetMapping()
//    public List<CarView> getAll() {
//        return carServiceImpl.getAll();
//    }
//
//    @GetMapping("/tax")
//    public CarTaxCalculateView tax(@RequestParam(required = true) long id, HttpServletRequest httpRequest) throws JsonProcessingException {
//        CarTaxRequest carTaxRequest = new CarTaxRequest();
//        carTaxRequest.setId(id);
//        carTaxRequest.setIpAddress(httpRequest.getRemoteAddr());
//        return taxService.queryCarTaxView(carTaxRequest);
//    }
//}
