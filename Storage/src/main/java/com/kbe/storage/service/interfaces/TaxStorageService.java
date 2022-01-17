package com.kbe.storage.service.interfaces;

import com.kbe.storage.domain.dto.tax.EditTaxRequest;
import com.kbe.storage.domain.dto.tax.TaxView;

import java.util.List;

public interface TaxStorageService {
    TaxView update(String id, EditTaxRequest request);

    TaxView create(EditTaxRequest request);

    TaxView delete(String id);

    TaxView get(String id);

    List<TaxView> getAll();
}
