package com.kbe.kompsys.repository;

import com.kbe.kompsys.domain.exception.NotFoundException;
import com.kbe.kompsys.domain.model.Car;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface CarRepository extends CrudRepository<Car, String> {
    default Car getCarById(String id) {
        return findById(id).orElseThrow(() -> new NotFoundException(Car.class, id));
    }
}
