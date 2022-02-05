package com.kbe.kompsys.service.interfaces;



import com.github.dergil.kompsys.dto.car.CarView;
import com.github.dergil.kompsys.dto.car.CreateCarRequest;
import com.github.dergil.kompsys.dto.car.DeleteCarRequest;
import com.github.dergil.kompsys.dto.car.EditCarRequest;

import java.util.List;

public interface CarService {
    public CarView create(CreateCarRequest request);

    public CarView update(EditCarRequest request);

    public CarView delete(DeleteCarRequest request);

    public CarView get(long id);

    public List<CarView> getAll() ;
}
