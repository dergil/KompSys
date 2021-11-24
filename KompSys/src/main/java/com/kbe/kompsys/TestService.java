//package com.kbe.kompsys;
//
//import com.kbe.kompsys.domain.dto.TaxResponse;
//import com.kbe.kompsys.domain.mapper.CarEditMapper;
//import com.kbe.kompsys.domain.mapper.CarTaxResponseMapper;
//import com.kbe.kompsys.domain.model.Car;
//import com.kbe.kompsys.repository.CarRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TestService {
//    @Autowired
//    private CarRepository carRepository;
//    @Autowired
//    private CarTaxResponseMapper carTaxResponseMapper;
//    @Autowired
//    private CarEditMapper carEditMapper;
//
//    public void test(){
//        Car car = carRepository.findAll().get(0);
////        System.out.println(car.toString());
//        TaxResponse taxResponse = carTaxResponseMapper.create(car);
//        taxResponse.setSalesTax(19);
//        taxResponse.setTaxAmount(100000);
////        System.out.println(taxResponse);
//    }
//}
