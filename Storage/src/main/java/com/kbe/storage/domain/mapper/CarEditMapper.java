package com.kbe.storage.domain.mapper;

import com.github.dergil.kompsys.dto.car.EditCarRequest;
import com.kbe.storage.domain.model.Car;
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
