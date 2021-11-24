package com.kbe.kompsys.domain.mapper;

import com.kbe.kompsys.domain.dto.TaxResponse;
import com.kbe.kompsys.domain.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarTaxResponseMapper {
    TaxResponse create(Car car);
}
