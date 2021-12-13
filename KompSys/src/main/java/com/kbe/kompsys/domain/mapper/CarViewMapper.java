package com.kbe.kompsys.domain.mapper;

import com.kbe.kompsys.domain.dto.car.CarView;
import com.kbe.kompsys.domain.model.Car;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarViewMapper {
    CarView toCarView(Car car);
}
