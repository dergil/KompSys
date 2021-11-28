package com.kbe.kompsys.domain.mapper;

import com.kbe.kompsys.domain.dto.EditCarRequest;
import com.kbe.kompsys.domain.model.Car;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface CarEditMapper {
    Car create(EditCarRequest request);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    void update(EditCarRequest request, @MappingTarget Car car);
}
