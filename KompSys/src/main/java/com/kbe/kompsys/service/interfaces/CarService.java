package com.kbe.kompsys.service.interfaces;



import com.github.dergil.kompsys.dto.car.*;

import java.util.List;

public interface CarService {
    CarView create(CreateCarRequest request);

    CarView update(EditCarRequest request);

    CarView delete(DeleteCarRequest request);

    CarView get(ReadCarRequest request);

    List<CarView> getAll(ReadAllCarsRequest request) ;
}
