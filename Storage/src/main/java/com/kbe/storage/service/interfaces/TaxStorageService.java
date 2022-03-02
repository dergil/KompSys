package com.kbe.storage.service.interfaces;

import com.github.dergil.kompsys.dto.tax.*;


public interface TaxStorageService {
  TaxView update(EditTaxRequest request);

  TaxView create(CreateTaxRequest request);

  TaxView delete(DeleteTaxRequest request);

  TaxView get(ReadTaxRequest request);

  TaxViewList getAll(ReadAllTaxesRequest request);

}
