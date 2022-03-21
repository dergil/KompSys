package com.kbe.storage.repository.interfaces;

import com.github.dergil.kompsys.dto.exception.NotFoundException;
import com.kbe.storage.domain.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
  default Car getCarById(long id) {
    return findById(id).orElseThrow(() -> new NotFoundException(Car.class, id));
  }
}
