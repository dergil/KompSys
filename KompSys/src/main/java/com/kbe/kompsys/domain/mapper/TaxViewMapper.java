package com.kbe.kompsys.domain.mapper;

import com.kbe.kompsys.domain.dto.TaxView;
import com.kbe.kompsys.domain.model.Tax;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaxViewMapper {
    TaxView toTaxview(Tax tax);
}
