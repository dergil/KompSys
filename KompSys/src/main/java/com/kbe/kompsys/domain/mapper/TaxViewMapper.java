package com.kbe.kompsys.domain.mapper;

import com.kbe.kompsys.domain.dto.tax.TaxView;
import com.kbe.kompsys.domain.model.Tax;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaxViewMapper {
    TaxView toTaxView(Tax tax);

}

