package com.kbe.kompsys.domain.mapper;

import com.kbe.kompsys.domain.dto.EditCarRequest;
import com.kbe.kompsys.domain.model.Car;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarEditMapper {
    Car create(EditCarRequest request);
}
