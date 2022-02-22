package com.kbe.kompsys.domain.mapper;

import com.github.dergil.kompsys.dto.calculate.CalculateResponse;
import com.github.dergil.kompsys.dto.calculate.CalculateView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CalculateViewMapper {
    CalculateView toCalculateView(CalculateResponse calculateResponse);

}
