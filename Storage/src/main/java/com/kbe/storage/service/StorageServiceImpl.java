package com.kbe.storage.service;

import com.kbe.storage.domain.dto.tax.EditTaxRequest;
import com.kbe.storage.domain.dto.tax.TaxView;
import com.kbe.storage.domain.mapper.TaxEditMapper;
import com.kbe.storage.domain.mapper.TaxViewMapper;
import com.kbe.storage.domain.model.Tax;
import com.kbe.storage.repository.TaxRepository;
import com.kbe.storage.service.interfaces.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private TaxRepository taxRepository;
    @Autowired
    private TaxEditMapper taxEditMapper;
    @Autowired
    private TaxViewMapper taxViewMapper;

    @Override
    public TaxView update(String id, EditTaxRequest request) {
        Tax tax = taxRepository.getTaxById(id);
        taxEditMapper.update(request, tax);
        tax = taxRepository.save(tax);
        return taxViewMapper.toTaxView(tax);
    }

    @Override
    public TaxView create(EditTaxRequest request) {
        Tax tax = taxEditMapper.create(request);
        taxRepository.save(tax);
        return taxViewMapper.toTaxView(tax);
    }

    @Override
    public TaxView delete(String id) {
        Tax tax = taxRepository.getTaxById(id);
        taxRepository.delete(tax);
        return taxViewMapper.toTaxView(tax);
    }

    @Override
    public TaxView get(String id) {
        Tax tax = taxRepository.getTaxById(id);
        return taxViewMapper.toTaxView(tax);
    }

    @Override
    public List<TaxView> getAll() {
        List<Tax> taxs = taxRepository.findAll();
        List<TaxView> taxViews = new ArrayList<>();
        for (Tax tax : taxs) {
            taxViews.add(taxViewMapper.toTaxView(tax));
        }
        return taxViews;
    }
}
