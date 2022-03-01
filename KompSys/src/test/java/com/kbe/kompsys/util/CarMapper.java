package com.kbe.kompsys.util;

import com.github.dergil.kompsys.dto.car.CarView;
import com.kbe.kompsys.domain.model.Car;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {
    Car toCar(CarView carView);
}
