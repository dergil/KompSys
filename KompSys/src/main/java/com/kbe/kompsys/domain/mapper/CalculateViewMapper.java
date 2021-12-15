package com.kbe.kompsys.domain.mapper;

import com.kbe.kompsys.domain.dto.calculate.CalculateResponse;
import com.kbe.kompsys.domain.dto.calculate.CalculateView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CalculateViewMapper {
    CalculateView toCalculateView(CalculateResponse calculateResponse);

}
