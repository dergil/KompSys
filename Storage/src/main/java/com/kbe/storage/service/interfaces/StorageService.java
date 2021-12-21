package com.kbe.storage.service.interfaces;

import com.kbe.storage.domain.dto.car.EditCarRequest;
import com.kbe.storage.domain.model.Car;

import java.util.List;

public interface StorageService {
    Car update(long id, EditCarRequest request);

    Car create(EditCarRequest request);

    Car delete(long id);

    Car get(long id);

    List<Car> getAll();
}
