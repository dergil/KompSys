package com.kbe.kompsys.domain.mapper;

import com.kbe.kompsys.domain.dto.tax.TaxResponse;
import com.kbe.kompsys.domain.model.Car;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Mapper(componentModel = "spring")
public interface CarTaxResponseMapper {
    TaxResponse create(Car car);
}
