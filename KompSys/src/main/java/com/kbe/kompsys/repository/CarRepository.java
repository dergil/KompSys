package com.kbe.kompsys.repository;

import com.github.dergil.kompsys.dto.exception.EntryNotFoundException;
import com.kbe.kompsys.domain.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CarRepository extends JpaRepository<Car, Long> {
    default Car getCarById(long id) {
        return findById(id).orElseThrow(() -> new EntryNotFoundException(Car.class, id));
    }

}
