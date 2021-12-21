package com.kbe.storage.service;


import com.kbe.storage.domain.dto.car.EditCarRequest;
import com.kbe.storage.domain.model.Car;
import com.kbe.storage.service.interfaces.StorageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {
    @Override
    public Car update(long id, EditCarRequest request) {
        return null;
    }

    @Override
    public Car create(EditCarRequest request) {
        return null;
    }

    @Override
    public Car delete(long id) {
        return null;
    }

    @Override
    public Car get(long id) {
        return null;
    }

    @Override
    public List<Car> getAll() {
        return null;
    }
}
