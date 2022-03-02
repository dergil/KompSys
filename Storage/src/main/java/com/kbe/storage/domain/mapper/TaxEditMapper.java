package com.kbe.storage.domain.mapper;

import com.github.dergil.kompsys.dto.tax.EditTaxRequest;
import com.kbe.storage.domain.model.Tax;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface TaxEditMapper {
  Tax create(EditTaxRequest request);

  @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
  void update(EditTaxRequest request, @MappingTarget Tax tax);
}
