package com.kbe.kompsys.service.interfaces;

import com.kbe.kompsys.domain.dto.car.CarView;
import com.kbe.kompsys.domain.dto.car.EditCarRequest;

import java.util.List;

public interface CarService {
    public CarView create(EditCarRequest request);

    public CarView update(long id, EditCarRequest request);

    public CarView delete(long id);

    public CarView get(long id);

    public List<CarView> getAll() ;
}
