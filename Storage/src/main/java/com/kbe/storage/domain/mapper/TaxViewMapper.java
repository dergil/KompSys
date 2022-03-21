package com.kbe.storage.domain.mapper;

import com.github.dergil.kompsys.dto.tax.TaxView;
import com.kbe.storage.domain.model.Tax;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaxViewMapper {
  TaxView toTaxView(Tax tax);

}


